package com.example.flickpick.domain.repository

import com.example.flickpick.domain.model.Movie
import com.example.flickpick.domain.model.MovieDetail
import com.example.flickpick.util.UiState
import kotlinx.coroutines.flow.Flow

/**
 * Repository contract for movie data operations.
 * Implementations handle networking, caching, and error mapping.
 */
interface MovieRepository {

    /** Fetches a list of currently popular movies. */
    fun getPopularMovies(): Flow<UiState<List<Movie>>>

    /** Fetches a list of top-rated movies. */
    fun getTopRatedMovies(): Flow<UiState<List<Movie>>>

    /** Searches movies matching the given [query]. */
    fun searchMovies(query: String): Flow<UiState<List<Movie>>>

    /** Fetches detailed information for the movie with the given [movieId]. */
    fun getMovieDetail(movieId: Int): Flow<UiState<MovieDetail>>

    // TODO: Return PagingData<Movie> for paged lists (Paging 3 integration)
}
