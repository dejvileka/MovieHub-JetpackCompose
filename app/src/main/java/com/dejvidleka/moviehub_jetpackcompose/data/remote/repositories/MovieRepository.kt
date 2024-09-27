package com.dejvidleka.moviehub_jetpackcompose.data.remote.repositories

import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieDetails
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.PopularMovies
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<PopularMovies>
}