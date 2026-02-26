package com.example.flickpick.data.remote

import com.example.flickpick.data.model.MovieDetailDto
import com.example.flickpick.data.model.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit service interface for the TMDB API.
 * API key is appended automatically by [ApiKeyInterceptor].
 */
interface ApiService {

    /** Fetches a paginated list of popular movies. */
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): MovieResponseDto

    /** Fetches a paginated list of top-rated movies. */
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1
    ): MovieResponseDto

    /** Searches movies by a text [query]. */
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): MovieResponseDto

    /** Fetches detailed info for a specific movie by [movieId]. */
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): MovieDetailDto
}
