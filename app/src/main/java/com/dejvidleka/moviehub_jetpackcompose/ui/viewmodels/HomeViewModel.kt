package com.dejvidleka.moviehub_jetpackcompose.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dejvidleka.core.usecases.GetTopRatedMoviesUseCase
import com.dejvidleka.data.di.Result
import com.dejvidleka.data.di.toResult
import com.dejvidleka.data.remote.models.MovieResult
import com.dejvidleka.data.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<Result<List<MovieResult>>>(Result.Loading())
    val uiState: StateFlow<Result<List<MovieResult>>> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getTopRatedMoviesUseCase().toResult().collect {result->
                Log.d("HomeViewModel", "Received result: $result")

                _uiState.value = result }
        }
    }
}