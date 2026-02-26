package com.example.flickpick.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flickpick.data.local.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for favourite movie operations.
 */
@Dao
interface FavouriteDao {

    /** Observes all favourite movies, newest first. */
    @Query("SELECT * FROM favourites ORDER BY addedAt DESC")
    fun getAllFavourites(): Flow<List<FavouriteEntity>>

    /** Adds a movie to favourites. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: FavouriteEntity)

    /** Removes a movie from favourites by its TMDB [movieId]. */
    @Query("DELETE FROM favourites WHERE movieId = :movieId")
    suspend fun deleteFavourite(movieId: Int)

    /** Observes whether the movie with [movieId] is a favourite. */
    @Query("SELECT EXISTS(SELECT 1 FROM favourites WHERE movieId = :movieId)")
    fun isFavourite(movieId: Int): Flow<Boolean>
}
