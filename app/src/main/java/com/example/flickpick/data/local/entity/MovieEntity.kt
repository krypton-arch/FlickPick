package com.example.flickpick.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for caching movie list items.
 * [category] distinguishes between "popular", "top_rated", and "genre_{id}" lists.
 */
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0,
    val movieId: Int,
    val title: String,
    val posterPath: String?,
    val voteAverage: Double,
    val releaseDate: String?,
    val overview: String,
    val category: String,
    val page: Int,
    val insertedAt: Long = System.currentTimeMillis()
)
