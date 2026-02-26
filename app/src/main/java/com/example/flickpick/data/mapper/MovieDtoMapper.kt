package com.example.flickpick.data.mapper

import com.example.flickpick.data.local.entity.FavouriteEntity
import com.example.flickpick.data.local.entity.GenreEntity
import com.example.flickpick.data.local.entity.MovieEntity
import com.example.flickpick.data.model.GenreDto
import com.example.flickpick.data.model.MovieDetailDto
import com.example.flickpick.data.model.MovieDto
import com.example.flickpick.domain.model.Genre
import com.example.flickpick.domain.model.Movie
import com.example.flickpick.domain.model.MovieDetail

/** Image base URL for TMDB poster assets. */
private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

/** Poster size used in list screens. */
private const val POSTER_SIZE_LIST = "w342"

/** Poster size used in the detail screen. */
private const val POSTER_SIZE_DETAIL = "w500"

// ── DTO → Domain ────────────────────────────────────────────────

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

// ── DTO → Entity ────────────────────────────────────────────────

/**
 * Maps a [MovieDto] to a [MovieEntity] for Room caching.
 */
fun MovieDto.toEntity(category: String, page: Int): MovieEntity = MovieEntity(
    movieId = id,
    title = title,
    posterPath = posterPath,
    voteAverage = voteAverage,
    releaseDate = releaseDate,
    overview = overview,
    category = category,
    page = page
)

/**
 * Maps a [GenreDto] to a [GenreEntity] for Room caching.
 */
fun GenreDto.toEntity(): GenreEntity = GenreEntity(
    id = id,
    name = name
)

// ── Entity → Domain ─────────────────────────────────────────────

/**
 * Maps a [MovieEntity] to a [Movie] domain model.
 */
fun MovieEntity.toDomain(): Movie = Movie(
    id = movieId,
    title = title,
    posterUrl = posterPath?.let { "${IMAGE_BASE_URL}${POSTER_SIZE_LIST}$it" } ?: "",
    rating = voteAverage,
    releaseYear = releaseDate?.take(4) ?: "N/A",
    overview = overview
)

/**
 * Maps a [FavouriteEntity] to a [Movie] domain model.
 */
fun FavouriteEntity.toDomain(): Movie = Movie(
    id = movieId,
    title = title,
    posterUrl = posterPath?.let { "${IMAGE_BASE_URL}${POSTER_SIZE_LIST}$it" } ?: "",
    rating = voteAverage,
    releaseYear = releaseDate?.take(4) ?: "N/A",
    overview = overview,
    isFavourite = true
)

/**
 * Maps a [GenreEntity] to a [Genre] domain model.
 */
fun GenreEntity.toDomain(): Genre = Genre(
    id = id,
    name = name
)

// ── Domain → Entity ─────────────────────────────────────────────

/**
 * Maps a [MovieDetail] to a [FavouriteEntity] for saving to favourites.
 */
fun MovieDetail.toFavouriteEntity(): FavouriteEntity = FavouriteEntity(
    movieId = id,
    title = title,
    posterPath = posterUrl.substringAfterLast("/").let {
        if (it.isNotEmpty()) "/$it" else null
    },
    voteAverage = rating,
    releaseDate = if (releaseYear != "N/A") "${releaseYear}-01-01" else null,
    overview = overview
)
