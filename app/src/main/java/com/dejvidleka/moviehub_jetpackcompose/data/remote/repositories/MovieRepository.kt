package com.dejvidleka.moviehub_jetpackcompose.data.remote.repositories

import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieDetails
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.PopularMovies
import com.dejvidleka.moviehub_jetpackcompose.data.result.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<Result<PopularMovies>>
}