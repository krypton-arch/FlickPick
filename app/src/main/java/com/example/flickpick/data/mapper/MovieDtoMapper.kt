package com.example.flickpick.data.mapper

import com.example.flickpick.data.model.MovieDetailDto
import com.example.flickpick.data.model.MovieDto
import com.example.flickpick.domain.model.Movie
import com.example.flickpick.domain.model.MovieDetail

/** Image base URL for TMDB poster assets. */
private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

/** Poster size used in list screens. */
private const val POSTER_SIZE_LIST = "w342"

/** Poster size used in the detail screen. */
private const val POSTER_SIZE_DETAIL = "w500"

/**
 * Maps a [MovieDto] from the data layer to a [Movie] domain model.
 */
fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    title = title,
    posterUrl = posterPath?.let { "${IMAGE_BASE_URL}${POSTER_SIZE_LIST}$it" } ?: "",
    rating = voteAverage,
    releaseYear = releaseDate?.take(4) ?: "N/A",
    overview = overview
)

/**
 * Maps a [MovieDetailDto] from the data layer to a [MovieDetail] domain model.
 */
fun MovieDetailDto.toDomain(): MovieDetail = MovieDetail(
    id = id,
    title = title,
    posterUrl = posterPath?.let { "${IMAGE_BASE_URL}${POSTER_SIZE_DETAIL}$it" } ?: "",
    rating = voteAverage,
    releaseYear = releaseDate?.take(4) ?: "N/A",
    overview = overview,
    genres = genres.map { it.name },
    language = originalLanguage.uppercase(),
    runtime = runtime ?: 0,
    tagline = tagline ?: ""
)
