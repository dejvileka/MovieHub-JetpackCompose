package com.dejvidleka.moviehub_jetpackcompose.di

import android.app.Application
import androidx.room.Room
import com.dejvidleka.moviehub_jetpackcompose.data.local.AppDatabase
import com.dejvidleka.moviehub_jetpackcompose.data.local.dao.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application, AppDatabase::class.java,
            name = "movie_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideFavoriteMovieDao(appDatabase: AppDatabase): FavoriteMovieDao =
        appDatabase.favoriteMovieDao()

}