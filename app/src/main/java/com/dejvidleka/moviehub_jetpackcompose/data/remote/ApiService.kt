package com.dejvidleka.moviehub_jetpackcompose.data.remote

import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieResponse
import retrofit2.http.GET

interface ApiService {
    //Popular
    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieResponse

    //Upcoming
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): MovieResponse

    //TopRated
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): MovieResponse

    //NowPlaying
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): MovieResponse


}
