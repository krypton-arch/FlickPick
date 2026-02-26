package com.example.flickpick.domain.model

/**
 * Domain model representing a movie in the app.
 * Mapped from the data layer DTOs — contains only the fields the UI needs.
 */
data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Double,
    val releaseYear: String,
    val overview: String,
    /** Placeholder for future favourites feature. */
    val isFavourite: Boolean = false
)
