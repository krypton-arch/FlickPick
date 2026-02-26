package com.example.flickpick.domain.model

/**
 * Domain model for detailed movie information.
 * Contains all fields from [Movie] plus additional detail-specific data.
 */
data class MovieDetail(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Double,
    val releaseYear: String,
    val overview: String,
    val genres: List<String>,
    val language: String,
    val runtime: Int,
    val tagline: String,
    val isFavourite: Boolean = false
)
