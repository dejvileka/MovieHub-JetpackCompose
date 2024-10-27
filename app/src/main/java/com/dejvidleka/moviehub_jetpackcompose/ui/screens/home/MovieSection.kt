package com.dejvidleka.moviehub_jetpackcompose.ui.screens.home

import androidx.compose.runtime.Composable
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieResponse
import com.dejvidleka.moviehub_jetpackcompose.data.result.Result
import com.dejvidleka.moviehub_jetpackcompose.ui.components.ErrorView
import com.dejvidleka.moviehub_jetpackcompose.ui.components.LoadingIndicator

@Composable
fun MovieSection(
    movieState: Result<MovieResponse>,
    onRetry: () -> Unit,
    content: @Composable (MovieResponse) -> Unit,
) {
    when (movieState) {
        is Result.Loading -> LoadingIndicator()
        is Result.Error -> ErrorView(
            message = movieState.message,
            onRetry = onRetry
        )
        is Result.Success ->content(movieState.data)
    }
}