package com.dejvidleka.moviehub_jetpackcompose.data.remote

import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.PopularMovies
import retrofit2.http.GET

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(

    ): PopularMovies
}
