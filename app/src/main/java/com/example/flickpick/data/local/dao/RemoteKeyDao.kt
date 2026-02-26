package com.example.flickpick.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flickpick.data.local.entity.RemoteKeyEntity

/**
 * DAO for paging remote keys.
 */
@Dao
interface RemoteKeyDao {

    /** Gets the remote key for the given [category]. */
    @Query("SELECT * FROM remote_keys WHERE category = :category")
    suspend fun getRemoteKey(category: String): RemoteKeyEntity?

    /** Inserts or replaces a remote key. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(key: RemoteKeyEntity)

    /** Clears the remote key for the given [category]. */
    @Query("DELETE FROM remote_keys WHERE category = :category")
    suspend fun clearByCategory(category: String)
}
