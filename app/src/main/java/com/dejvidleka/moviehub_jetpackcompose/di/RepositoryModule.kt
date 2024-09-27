package com.dejvidleka.moviehub_jetpackcompose.di

import com.dejvidleka.moviehub_jetpackcompose.data.remote.repositories.MovieRepository
import com.dejvidleka.moviehub_jetpackcompose.data.remote.repositories.MovieRepositoryImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(movieRepository: MovieRepositoryImplementation): MovieRepository
}