package com.dejvidleka.moviehub_jetpackcompose.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dejvidleka.moviehub_jetpackcompose.data.remote.Result
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.PopularMovies
import com.dejvidleka.moviehub_jetpackcompose.data.remote.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _popularMovies = MutableStateFlow<Result<PopularMovies>>(Result.Loading())
    val popularMovies: StateFlow<Result<PopularMovies>> = _popularMovies.asStateFlow()


    init {
        viewModelScope.launch {
            repository.getPopularMovies()
                .onStart {
                    _popularMovies.value = Result.Loading()
                }
                .catch { e ->
                    Log.e("home viewmodel", "$e")
                }
                .collectLatest { popularMovies ->
                    _popularMovies.value = Result.Success(popularMovies)
                }
        }
    }
}