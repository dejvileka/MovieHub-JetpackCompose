package com.dejvidleka.moviehub_jetpackcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dejvidleka.moviehub_jetpackcompose.data.local.entity.MovieDetailsEntity
import com.dejvidleka.moviehub_jetpackcompose.data.local.entity.MoviePageInfoEntity


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieDetailsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPageInfo(pageInfo: MoviePageInfoEntity)

    @Query("SELECT * FROM MOVIES WHERE type = :type")
    suspend fun getMoviesByType(type: String): List<MovieDetailsEntity>

    @Query("SELECT * FROM movie_page_info WHERE type = :type")
    suspend fun getPageInfoByType(type: String): MoviePageInfoEntity?

    @Query("DELETE FROM movies WHERE type = :type")
    suspend fun clearMoviesByType(type: String)
}