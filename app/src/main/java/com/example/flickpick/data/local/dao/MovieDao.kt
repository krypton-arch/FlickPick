package com.example.flickpick.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flickpick.data.local.entity.MovieEntity

/**
 * DAO for cached movie list items.
 * Provides a [PagingSource] for Paging 3 integration.
 */
@Dao
interface MovieDao {

    /** Returns a PagingSource of movies for the given [category]. */
    @Query("SELECT * FROM movies WHERE category = :category ORDER BY page ASC, localId ASC")
    fun getMoviesByCategory(category: String): PagingSource<Int, MovieEntity>

    /** Inserts a list of movies into the cache. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    /** Clears all cached movies for the given [category]. */
    @Query("DELETE FROM movies WHERE category = :category")
    suspend fun clearByCategory(category: String)
}
