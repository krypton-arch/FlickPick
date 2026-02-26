package com.example.flickpick.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.flickpick.data.local.FlickPickDatabase
import com.example.flickpick.data.local.dao.FavouriteDao
import com.example.flickpick.data.local.dao.GenreDao
import com.example.flickpick.data.mapper.toDomain
import com.example.flickpick.data.mapper.toEntity
import com.example.flickpick.data.mapper.toFavouriteEntity
import com.example.flickpick.data.paging.MovieRemoteMediator
import com.example.flickpick.data.paging.SearchPagingSource
import com.example.flickpick.data.remote.ApiService
import com.example.flickpick.domain.model.Genre
import com.example.flickpick.domain.model.Movie
import com.example.flickpick.domain.model.MovieDetail
import com.example.flickpick.domain.repository.MovieRepository
import com.example.flickpick.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val PAGE_SIZE = 20

/**
 * Concrete implementation of [MovieRepository] backed by TMDB [ApiService] and Room.
 */
@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val database: FlickPickDatabase,
    private val favouriteDao: FavouriteDao,
    private val genreDao: GenreDao
) : MovieRepository {

    // ── Paged movie lists ───────────────────────────────────────

    @OptIn(ExperimentalPagingApi::class)
    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = MovieRemoteMediator(
                category = "popular",
                database = database,
                fetchPage = { page -> apiService.getPopularMovies(page) }
            ),
            pagingSourceFactory = { database.movieDao().getMoviesByCategory("popular") }
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = MovieRemoteMediator(
                category = "top_rated",
                database = database,
                fetchPage = { page -> apiService.getTopRatedMovies(page) }
            ),
            pagingSourceFactory = { database.movieDao().getMoviesByCategory("top_rated") }
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { SearchPagingSource(apiService, query) }
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun discoverByGenre(genreId: Int): Flow<PagingData<Movie>> {
        val category = "genre_$genreId"
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = MovieRemoteMediator(
                category = category,
                database = database,
                fetchPage = { page -> apiService.discoverMoviesByGenre(genreId, page) }
            ),
            pagingSourceFactory = { database.movieDao().getMoviesByCategory(category) }
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }

    // ── Single fetch ────────────────────────────────────────────

    override fun getMovieDetail(movieId: Int): Flow<UiState<MovieDetail>> = flow {
        emit(UiState.Loading)
        val result = apiService.getMovieDetail(movieId)
        emit(UiState.Success(result.toDomain()))
    }.catch { e ->
        emit(UiState.Error(e.localizedMessage ?: "Unknown error"))
    }.flowOn(Dispatchers.IO)

    // ── Genres ──────────────────────────────────────────────────

    override fun getGenres(): Flow<UiState<List<Genre>>> = flow {
        emit(UiState.Loading)
        // Fetch from network and cache
        val response = apiService.getGenreList()
        val entities = response.genres.map { it.toEntity() }
        genreDao.clearAll()
        genreDao.insertAll(entities)
        // Emit cached
        emit(UiState.Success(entities.map { it.toDomain() }))
    }.catch { e ->
        // On error, try to emit from cache
        emit(UiState.Error(e.localizedMessage ?: "Failed to load genres"))
    }.flowOn(Dispatchers.IO)

    // ── Favourites ──────────────────────────────────────────────

    override fun getFavourites(): Flow<List<Movie>> {
        return favouriteDao.getAllFavourites().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun toggleFavourite(movie: MovieDetail) {
        val isFav = favouriteDao.isFavourite(movie.id).first()
        if (isFav) {
            favouriteDao.deleteFavourite(movie.id)
        } else {
            favouriteDao.insertFavourite(movie.toFavouriteEntity())
        }
    }

    override fun isFavourite(movieId: Int): Flow<Boolean> {
        return favouriteDao.isFavourite(movieId)
    }
}
