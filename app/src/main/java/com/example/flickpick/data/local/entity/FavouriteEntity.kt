package com.example.flickpick.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for movies saved as favourites by the user.
 */
@Entity(tableName = "favourites")
data class FavouriteEntity(
    @PrimaryKey
    val movieId: Int,
    val title: String,
    val posterPath: String?,
    val voteAverage: Double,
    val releaseDate: String?,
    val overview: String,
    val addedAt: Long = System.currentTimeMillis()
)
