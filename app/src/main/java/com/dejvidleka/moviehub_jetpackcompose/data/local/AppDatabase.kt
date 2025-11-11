package com.dejvidleka.moviehub_jetpackcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dejvidleka.moviehub_jetpackcompose.data.local.dao.FavoriteMovieDao
import com.dejvidleka.moviehub_jetpackcompose.data.local.dao.MovieDao
import com.dejvidleka.moviehub_jetpackcompose.data.local.entity.MovieDetailsEntity
import com.dejvidleka.moviehub_jetpackcompose.data.local.entity.MoviePageInfoEntity

@Database(
    entities = [MovieDetailsEntity::class, MoviePageInfoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun movieDao(): MovieDao
}
