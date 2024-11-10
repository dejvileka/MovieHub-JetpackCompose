package com.dejvidleka.moviehub_jetpackcompose.data.remote

import android.os.Build
import androidx.annotation.RequiresApi
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

interface ApiService {
    //Popular
    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieResponse

    //Upcoming
    @RequiresApi(Build.VERSION_CODES.O)
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 2
    ): MovieResponse

    //TopRated
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): MovieResponse

    //NowPlaying
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): MovieResponse

    //Tending
    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrendingMovies(
        @Path("media_type") mediaType: String = "movie",
        @Path("time_window") timeWindow: String = "day",
        @Query("page") page: Int = 1
    ): MovieResponse
}
