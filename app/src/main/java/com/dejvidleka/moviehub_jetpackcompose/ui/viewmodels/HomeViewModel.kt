package com.dejvidleka.moviehub_jetpackcompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.dejvidleka.data.di.Result
import com.dejvidleka.data.di.toResult
import com.dejvidleka.data.remote.models.MovieResult
import com.dejvidleka.data.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    fun getTopRatedMovies(): Flow<Result<List<MovieResult>>> {
        return movieRepository.getTopRated().toResult()
    }
}