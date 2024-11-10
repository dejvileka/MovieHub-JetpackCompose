package com.dejvidleka.moviehub_jetpackcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dejvidleka.moviehub_jetpackcompose.data.local.entity.MovieDetailsEntity
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovies(movieDetails: MovieDetailsEntity)

    @Query("SELECT * FROM FAVORITE_MOVIES")
    fun getFavoriteMovies(): Flow<List<MovieDetails>>

    @Query("SELECT * FROM FAVORITE_MOVIES WHERE id = :id")
    fun getFavoriteMovieById(id: String): Flow<List<MovieDetails>>

    @Query("SELECT id FROM FAVORITE_MOVIES")
    fun getFavoriteMoviesId(): Flow<List<Int>>

    @Query("DELETE FROM FAVORITE_MOVIES WHERE id =:id")
    suspend fun deleteMovieFromFavorite(id: Int)
}