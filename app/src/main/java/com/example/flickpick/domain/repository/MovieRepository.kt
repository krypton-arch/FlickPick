package com.example.flickpick.domain.repository

import androidx.paging.PagingData
import com.example.flickpick.domain.model.Genre
import com.example.flickpick.domain.model.Movie
import com.example.flickpick.domain.model.MovieDetail
import com.example.flickpick.util.UiState
import kotlinx.coroutines.flow.Flow

/**
 * Repository contract for movie data operations.
 * Implementations handle networking, caching, and error mapping.
 */
interface MovieRepository {

    /** Returns a paged stream of currently popular movies. */
    fun getPopularMovies(): Flow<PagingData<Movie>>

    /** Returns a paged stream of top-rated movies. */
    fun getTopRatedMovies(): Flow<PagingData<Movie>>

    /** Returns a paged stream of movies matching the given [query]. */
    fun searchMovies(query: String): Flow<PagingData<Movie>>

    /** Returns a paged stream of movies filtered by [genreId]. */
    fun discoverByGenre(genreId: Int): Flow<PagingData<Movie>>

    /** Fetches detailed information for the movie with the given [movieId]. */
    fun getMovieDetail(movieId: Int): Flow<UiState<MovieDetail>>

    /** Fetches the list of all movie genres (cached locally). */
    fun getGenres(): Flow<UiState<List<Genre>>>

    /** Observes the list of favourite movies. */
    fun getFavourites(): Flow<List<Movie>>

    /** Toggles the favourite state of the given movie detail. */
    suspend fun toggleFavourite(movie: MovieDetail)

    /** Observes whether the movie with [movieId] is a favourite. */
    fun isFavourite(movieId: Int): Flow<Boolean>
}
