package com.dejvidleka.moviehub_jetpackcompose.data.remote.repositories

import android.util.Log
import com.dejvidleka.moviehub_jetpackcompose.data.remote.ApiService
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.PopularMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImplementation @Inject constructor(
    private val apiService: ApiService
) : MovieRepository {

    override fun getPopularMovies(): Flow<PopularMovies> = flow {
        val response = apiService.getPopularMovies()
        Log.d("responserepo", "$response")
        emit(response)
    }
}