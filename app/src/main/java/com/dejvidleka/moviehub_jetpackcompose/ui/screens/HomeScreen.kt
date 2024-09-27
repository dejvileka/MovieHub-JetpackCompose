package com.dejvidleka.moviehub_jetpackcompose.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.dejvidleka.moviehub_jetpackcompose.data.remote.Result
import com.dejvidleka.moviehub_jetpackcompose.ui.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val popularUiState by viewModel.popularMovies.collectAsState()
    LazyColumn {
        when (val state = popularUiState) {
            is Result.Success -> {
                items(state.data.results) { movies ->
                    Text(movies.title)
                }
            }
            is Result.Loading -> {}
            is Result.Error<*> -> {}
        }

    }
}
