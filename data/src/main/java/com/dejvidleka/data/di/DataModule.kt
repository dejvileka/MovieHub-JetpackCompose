package com.dejvidleka.data.di

import com.dejvidleka.data.remote.TMDBApiService
import com.dejvidleka.data.repositories.MovieRepository
import com.dejvidleka.data.repositories.MovieRepositoryIMP
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideMovieRepository(tmdbApiService: TMDBApiService): MovieRepository {
        return MovieRepositoryIMP(tmdbApiService)
    }
}