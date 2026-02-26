package com.example.flickpick.data.repository

import com.example.flickpick.data.mapper.toDomain
import com.example.flickpick.data.remote.ApiService
import com.example.flickpick.domain.model.Movie
import com.example.flickpick.domain.model.MovieDetail
import com.example.flickpick.domain.repository.MovieRepository
import com.example.flickpick.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Concrete implementation of [MovieRepository] backed by the TMDB [ApiService].
 * Every API call is wrapped in a Flow that emits Loading → Success or Error.
 */
@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MovieRepository {

    // TODO: emit from Room first, then refresh from network

    override fun getPopularMovies(): Flow<UiState<List<Movie>>> = flow {
        emit(UiState.Loading)
        val result = apiService.getPopularMovies()
        emit(UiState.Success(result.results.map { it.toDomain() }))
    }.catch { e ->
        emit(UiState.Error(e.localizedMessage ?: "Unknown error"))
    }.flowOn(Dispatchers.IO)

    override fun getTopRatedMovies(): Flow<UiState<List<Movie>>> = flow {
        emit(UiState.Loading)
        val result = apiService.getTopRatedMovies()
        emit(UiState.Success(result.results.map { it.toDomain() }))
    }.catch { e ->
        emit(UiState.Error(e.localizedMessage ?: "Unknown error"))
    }.flowOn(Dispatchers.IO)

    override fun searchMovies(query: String): Flow<UiState<List<Movie>>> = flow {
        emit(UiState.Loading)
        val result = apiService.searchMovies(query)
        emit(UiState.Success(result.results.map { it.toDomain() }))
    }.catch { e ->
        emit(UiState.Error(e.localizedMessage ?: "Unknown error"))
    }.flowOn(Dispatchers.IO)

    override fun getMovieDetail(movieId: Int): Flow<UiState<MovieDetail>> = flow {
        emit(UiState.Loading)
        val result = apiService.getMovieDetail(movieId)
        emit(UiState.Success(result.toDomain()))
    }.catch { e ->
        emit(UiState.Error(e.localizedMessage ?: "Unknown error"))
    }.flowOn(Dispatchers.IO)
}
