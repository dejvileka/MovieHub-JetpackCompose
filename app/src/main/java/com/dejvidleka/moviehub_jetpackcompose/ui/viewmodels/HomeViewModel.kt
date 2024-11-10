package com.dejvidleka.moviehub_jetpackcompose.ui.viewmodels

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieDetails
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieResponse
import com.dejvidleka.moviehub_jetpackcompose.data.remote.repositories.MovieRepository
import com.dejvidleka.moviehub_jetpackcompose.data.result.Result
import com.dejvidleka.moviehub_jetpackcompose.ui.screens.home.MovieType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {
    private val movieStates =
        mutableStateMapOf<MovieType, MutableStateFlow<Result<MovieResponse>>>()

    init {
        MovieType.entries.forEach { type ->
            movieStates[type] = MutableStateFlow(Result.Loading)
        }
        loadAllMovies()
    }

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _favoriteMovieIds: StateFlow<Set<Int>> = repository.getFavoriteMovieIds().stateIn(
        viewModelScope, SharingStarted.Lazily, emptySet()
    )

    fun isMovieFavorite(movieId: Int): StateFlow<Boolean> {
        return _favoriteMovieIds.map { favoriteIds ->
            movieId in favoriteIds
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            false
        )
    }

    fun toggleFavorite(movie: MovieDetails) {
        viewModelScope.launch {
            if (movie.id in _favoriteMovieIds.value) {
                repository.deleteMovieFromFavorites(movie.id)
            } else {
                repository.insertMovieToFavorites(movie)
            }
        }
    }


    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            delay(3000L)
            try {
                loadAllMovies()
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    private fun loadAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            MovieType.entries.forEach { type ->
                loadMovies(type)
            }
        }
    }

    private fun loadMovies(type: MovieType) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMovies(type).onStart { movieStates[type]?.value = Result.Loading }
                .catch { e ->
                    movieStates[type]?.value = Result.Error(
                        message = e.localizedMessage ?: "Unknown error", throwable = e
                    )
                }.collectLatest { result ->
                    movieStates[type]?.value = result
                }
        }
    }



    fun getMovieState(type: MovieType): StateFlow<Result<MovieResponse>> =
        movieStates[type]?.asStateFlow() ?: MutableStateFlow(Result.Loading)

    fun retry() {
        loadAllMovies()
    }
}

