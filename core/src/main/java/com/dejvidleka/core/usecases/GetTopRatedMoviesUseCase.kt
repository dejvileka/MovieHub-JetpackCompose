package com.dejvidleka.core.usecases

import com.dejvidleka.data.di.Result
import com.dejvidleka.data.di.toResult
import com.dejvidleka.data.remote.models.MovieResult
import com.dejvidleka.data.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val moviesRepository: MovieRepository
) {
    operator fun invoke():
            Flow<List<MovieResult>> = moviesRepository.getTopRated()
}