package com.example.flickpick.data.model

import com.google.gson.annotations.SerializedName

/**
 * DTO matching the TMDB movie detail JSON structure.
 */
data class MovieDetailDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("genres")
    val genres: List<GenreDto>,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("runtime")
    val runtime: Int?,

    @SerializedName("tagline")
    val tagline: String?
)
