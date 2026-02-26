package com.example.flickpick.data.model

import com.google.gson.annotations.SerializedName

/**
 * DTO wrapping the genre list response from TMDB's genre/movie/list endpoint.
 */
data class GenreListResponseDto(
    @SerializedName("genres")
    val genres: List<GenreDto>
)
