package com.dejvidleka.moviehub_jetpackcompose.data.remote.repositories

import com.dejvidleka.moviehub_jetpackcompose.data.remote.ApiService
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieResponse
import com.dejvidleka.moviehub_jetpackcompose.data.result.NetworkBoundResponse
import com.dejvidleka.moviehub_jetpackcompose.data.result.Result
import com.dejvidleka.moviehub_jetpackcompose.ui.screens.home.MovieType
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ViewModelScoped
class MovieRepository @Inject constructor(
    private val apiService: ApiService,
) {

    fun getMovies(movieType: MovieType): Flow<Result<MovieResponse>> {
        return NetworkBoundResponse(
            fetchFromNetwork = {
                when (movieType) {
                    MovieType.POPULAR -> {
                        apiService.getPopularMovies()
                    }

                    MovieType.TOP_RATED -> {
                        apiService.getTopRatedMovies()
                    }

                    MovieType.UPCOMING -> {
                        apiService.getUpcomingMovies()
                    }

                    MovieType.NOW_PLAYING -> {
                        apiService.getNowPlayingMovies()
                    }

                    MovieType.TRENDING -> {
                        apiService.getTrendingMovies()
                    }
                }
            },
            shouldFetch = { localData ->
                localData == null
            },
            saveLocally = {
                when (movieType) {
                    MovieType.POPULAR -> {
                        apiService.getPopularMovies()
                    }

                    MovieType.TOP_RATED -> {
                        apiService.getTopRatedMovies()
                    }

                    MovieType.UPCOMING -> {
                        apiService.getUpcomingMovies()
                    }

                    MovieType.NOW_PLAYING -> {
                        apiService.getNowPlayingMovies()
                    }

                    MovieType.TRENDING -> {
                        apiService.getTrendingMovies()
                    }
                }
            },
            getLocally = {
                when (movieType) {
                    MovieType.POPULAR -> {
                        apiService.getPopularMovies()
                    }

                    MovieType.TOP_RATED -> {
                        apiService.getTopRatedMovies()
                    }

                    MovieType.UPCOMING -> {
                        apiService.getUpcomingMovies()
                    }

                    MovieType.NOW_PLAYING -> {
                        apiService.getNowPlayingMovies()
                    }
                    MovieType.TRENDING -> {
                        apiService.getTrendingMovies()
                    }
                }
            },
        ).asFlow().flowOn(Dispatchers.IO)
    }
}
