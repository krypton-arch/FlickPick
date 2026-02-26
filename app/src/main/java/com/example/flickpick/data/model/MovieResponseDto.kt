package com.example.flickpick.data.model

import com.google.gson.annotations.SerializedName

/**
 * DTO wrapping the paginated movie list response from TMDB.
 */
data class MovieResponseDto(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<MovieDto>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
)
