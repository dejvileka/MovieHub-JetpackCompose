package com.dejvidleka.moviehub_jetpackcompose.data.remote.repositories

import com.dejvidleka.moviehub_jetpackcompose.data.remote.ApiService
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.PopularMovies
import com.dejvidleka.moviehub_jetpackcompose.data.result.NetworkBoundResponse
import com.dejvidleka.moviehub_jetpackcompose.data.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImplementation @Inject constructor(
    private val apiService: ApiService,
) : MovieRepository {

    override fun getPopularMovies(): Flow<Result<PopularMovies>> {
        return NetworkBoundResponse(
            fetchFromNetwork = {
                apiService.getPopularMovies()
            },
            shouldFetch = { localData ->
                localData == null
            },
            saveLocally = {
                apiService.getPopularMovies()
            },
            getLocally = {
                apiService.getPopularMovies()
            },
        ).asFlow()
            .flowOn(Dispatchers.IO)
    }
}
