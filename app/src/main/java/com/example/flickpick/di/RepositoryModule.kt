package com.example.flickpick.di

import com.example.flickpick.data.repository.MovieRepositoryImpl
import com.example.flickpick.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt module that binds the [MovieRepository] interface to its implementation.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /** Binds [MovieRepositoryImpl] as the concrete [MovieRepository]. */
    @Binds
    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository
}
