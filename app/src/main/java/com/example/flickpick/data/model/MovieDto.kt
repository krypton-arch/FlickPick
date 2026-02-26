package com.example.flickpick.data.model

import com.google.gson.annotations.SerializedName

/**
 * DTO matching the TMDB movie list item JSON structure.
 */
data class MovieDto(
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
    val overview: String
)
