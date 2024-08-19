package com.dejvidleka.data.remote.models

data class SearchResultMovies(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)
