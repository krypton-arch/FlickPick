package com.example.flickpick.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flickpick.data.local.dao.FavouriteDao
import com.example.flickpick.data.local.dao.GenreDao
import com.example.flickpick.data.local.dao.MovieDao
import com.example.flickpick.data.local.dao.RemoteKeyDao
import com.example.flickpick.data.local.entity.FavouriteEntity
import com.example.flickpick.data.local.entity.GenreEntity
import com.example.flickpick.data.local.entity.MovieEntity
import com.example.flickpick.data.local.entity.RemoteKeyEntity

/**
 * Room database for FlickPick.
 * Stores cached movies, genres, favourites, and paging remote keys.
 */
@Database(
    entities = [
        MovieEntity::class,
        GenreEntity::class,
        FavouriteEntity::class,
        RemoteKeyEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FlickPickDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
    abstract fun favouriteDao(): FavouriteDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}
