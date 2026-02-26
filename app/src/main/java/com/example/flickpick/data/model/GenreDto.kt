package com.example.flickpick.data.model

import com.google.gson.annotations.SerializedName

/**
 * DTO for a genre object returned by TMDB.
 */
data class GenreDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)
