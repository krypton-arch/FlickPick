package com.example.flickpick.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flickpick.data.local.entity.GenreEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for the cached genre list.
 */
@Dao
interface GenreDao {

    /** Observes all cached genres. */
    @Query("SELECT * FROM genres ORDER BY name ASC")
    fun getAllGenres(): Flow<List<GenreEntity>>

    /** Inserts or replaces all genres. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<GenreEntity>)

    /** Clears all cached genres. */
    @Query("DELETE FROM genres")
    suspend fun clearAll()
}
