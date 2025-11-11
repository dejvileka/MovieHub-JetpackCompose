package com.dejvidleka.moviehub_jetpackcompose.ui.screens.home.components

import androidx.compose.runtime.State
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieResponse
import com.dejvidleka.moviehub_jetpackcompose.data.result.Result

data class MovieCategory(
    val title: String,
    val movieState: State<Result<MovieResponse>>,
    val isCarousel: Boolean = false
)