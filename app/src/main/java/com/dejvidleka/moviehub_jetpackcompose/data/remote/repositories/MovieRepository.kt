package com.dejvidleka.moviehub_jetpackcompose.data.remote.repositories

import com.dejvidleka.moviehub_jetpackcompose.data.local.dao.FavoriteMovieDao
import com.dejvidleka.moviehub_jetpackcompose.data.local.dao.MovieDao
import com.dejvidleka.moviehub_jetpackcompose.data.local.entity.MovieDetailsEntity
import com.dejvidleka.moviehub_jetpackcompose.data.local.entity.MoviePageInfoEntity
import com.dejvidleka.moviehub_jetpackcompose.data.remote.ApiService
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieDetails
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieResponse
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.toMovieDetails
import com.dejvidleka.moviehub_jetpackcompose.data.result.NetworkBoundResponse
import com.dejvidleka.moviehub_jetpackcompose.data.result.Result
import com.dejvidleka.moviehub_jetpackcompose.ui.screens.home.MovieType
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class MovieRepository @Inject constructor(
    private val apiService: ApiService,
    private val favoriteMovieDao: FavoriteMovieDao,
    private val movieDao: MovieDao,

) {

    suspend fun insertMovieToFavorites(movieDetail: MovieDetails) {
        val movieDetails = movieDetail.toMovieDetails()
        favoriteMovieDao.insertFavoriteMovies(movieDetails)
    }

    suspend fun deleteMovieFromFavorites(id: Int) {
        favoriteMovieDao.deleteMovieFromFavorite(id)
    }

    fun getFavoriteMovieIds(): Flow<Set<Int>> {
        return favoriteMovieDao.getFavoriteMoviesId().map { ids-> ids.toSet() }
    }

    fun getMovies(movieType: MovieType): Flow<Result<MovieResponse>> {
        return NetworkBoundResponse(
            fetchFromNetwork = {
                fetchFromNetwork(movieType)
            },
            shouldFetch = { localData->
                localData == null
            },
            saveLocally = { networkResponse->
                saveMoviesLocally(movieType, networkResponse)
            },
            getLocally = {
                getMoviesLocally(movieType)
            }
        ).asFlow()
    }


    private suspend fun fetchFromNetwork(movieType: MovieType): MovieResponse {
        return when (movieType) {
            MovieType.POPULAR     -> apiService.getPopularMovies()
            MovieType.TOP_RATED   -> apiService.getTopRatedMovies()
            MovieType.UPCOMING    -> apiService.getUpcomingMovies()
            MovieType.NOW_PLAYING -> apiService.getNowPlayingMovies()
            MovieType.TRENDING    -> apiService.getTrendingMovies()
            MovieType.LATEST      -> apiService.getLatestMovies()
        }
    }


    private suspend fun saveMoviesLocally(movieType: MovieType, response: MovieResponse) {
        withContext(Dispatchers.IO) {
            val movieEntities = response.results.map { movie->
                MovieDetailsEntity(
                    id = movie.id,
                    adult = movie.adult,
                    backdrop_path = movie.backdrop_path,
                    genre_ids = movie.genre_ids,
                    original_language = movie.original_language,
                    original_title = movie.original_title,
                    overview = movie.overview,
                    popularity = movie.popularity,
                    poster_path = movie.poster_path,
                    release_date = movie.release_date,
                    title = movie.title,
                    video = movie.video,
                    vote_average = movie.vote_average,
                    vote_count = movie.vote_count,
                    type = movieType.name,
                    timeStamp = System.currentTimeMillis()
                )
            }

            movieDao.clearMoviesByType(movieType.name)
            movieDao.insertMovies(movieEntities)
            val moviePageInfo = MoviePageInfoEntity(
                type = movieType.name,
                page = response.page,
                totalPages = response.totalPages,
                totalResults = response.totalResults,
                timestamp = System.currentTimeMillis()
            )
            movieDao.insertPageInfo(moviePageInfo)
        }
    }

    private suspend fun getMoviesLocally(movieType: MovieType): MovieResponse? {
        return withContext(Dispatchers.IO) {
            val movies = movieDao.getMoviesByType(movieType.name)
            val pageInfo = movieDao.getPageInfoByType(movieType.name)

            if (movies.isEmpty() || pageInfo == null) {
                null
            } else {
                MovieResponse(
                    page = pageInfo.page,
                    results = movies.map { entity->
                        MovieDetails(
                            id = entity.id,
                            title = entity.title,
                            adult = entity.adult,
                            backdrop_path = entity.backdrop_path,
                            genre_ids = entity.genre_ids,
                            original_language = entity.original_language,
                            original_title = entity.original_title,
                            overview = entity.overview,
                            popularity = entity.popularity,
                            poster_path = entity.poster_path,
                            release_date = entity.release_date,
                            video = entity.video,
                            vote_average = entity.vote_average,
                            vote_count = entity.vote_count,
                            type = entity.type,
                            timeStamp = entity.timeStamp,
                        )
                    },
                    totalPages = pageInfo.totalPages,
                    totalResults = pageInfo.totalResults
                )
            }
        }
    }

    private suspend fun isDataStale(movieType: MovieType): Boolean {
        return withContext(Dispatchers.IO) {
            val pageInfo = movieDao.getPageInfoByType(movieType.name)
            if (pageInfo == null) {
                true
            } else {
                // Check if data is older than 1 hour (3600000 milliseconds)
                val oneHourAgo = System.currentTimeMillis() - 3600000
                pageInfo.timestamp < oneHourAgo
            }
        }
    }
}