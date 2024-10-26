package com.dejvidleka.moviehub_jetpackcompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.PopularMovies
import com.dejvidleka.moviehub_jetpackcompose.data.remote.repositories.MovieRepository
import com.dejvidleka.moviehub_jetpackcompose.data.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {
    private val _popularMovies = MutableStateFlow<Result<PopularMovies>>(Result.Loading)
    val popularMovies: StateFlow<Result<PopularMovies>> = _popularMovies.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                launch { collectMovies(repository::getPopularMovies, _popularMovies) }

            }
        }
    }

    private suspend fun <T> collectMovies(
        fetch: () -> Flow<Result<T>>, state: MutableStateFlow<Result<T>>
    ) {
        fetch().onStart { state.value = Result.Loading }
                .catch { e ->
                    state.value = Result.Error(
                        message = e.localizedMessage ?: "Unknown error occurred",
                        throwable = e
                    )
                    Timber.e(e)
                }.collectLatest { result ->
                state.value = result
                }
    }

    fun retry() {
        loadMovies()
    }
}

