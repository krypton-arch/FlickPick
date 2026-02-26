package com.example.flickpick.di

import android.content.Context
import androidx.room.Room
import com.example.flickpick.data.local.FlickPickDatabase
import com.example.flickpick.data.local.dao.FavouriteDao
import com.example.flickpick.data.local.dao.GenreDao
import com.example.flickpick.data.local.dao.MovieDao
import com.example.flickpick.data.local.dao.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module providing the Room database and all DAOs.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FlickPickDatabase {
        return Room.databaseBuilder(
            context,
            FlickPickDatabase::class.java,
            "flickpick_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideMovieDao(db: FlickPickDatabase): MovieDao = db.movieDao()

    @Provides
    fun provideGenreDao(db: FlickPickDatabase): GenreDao = db.genreDao()

    @Provides
    fun provideFavouriteDao(db: FlickPickDatabase): FavouriteDao = db.favouriteDao()

    @Provides
    fun provideRemoteKeyDao(db: FlickPickDatabase): RemoteKeyDao = db.remoteKeyDao()
}
