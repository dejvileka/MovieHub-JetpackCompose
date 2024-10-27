package com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie

data class MovieResponse(
    val dates: Dates? = null,
    val page: Int,
    val results: List<MovieDetails>,
    val total_pages: Int,
    val total_results: Int
)