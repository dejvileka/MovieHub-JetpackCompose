package com.dejvidleka.data.repositories

import com.dejvidleka.data.remote.TMDBApiService
import com.dejvidleka.data.remote.models.MovieResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryIMP @Inject constructor(
    private val tmdbApiService: TMDBApiService
) : MovieRepository {
    override fun getTopRated(): Flow<List<MovieResult>> {
        return flow {
            val movies = tmdbApiService.getTopRatedMovies(1)
            emit(movies.body()?.movieResults ?: emptyList())
        }
    }

}