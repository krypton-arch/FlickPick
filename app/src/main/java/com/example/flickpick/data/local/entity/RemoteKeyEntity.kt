package com.example.flickpick.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tracks the next page to fetch for each movie category.
 * Used by [MovieRemoteMediator] to know where to resume paging.
 */
@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey
    val category: String,
    val nextPage: Int?
)
