package com.dejvidleka.data.remote.models

data class Trailer(
    val id: Int,
    val results: List<TrailerResult>
)
data class TrailerResult(
    val id: String,
    val iso_3166_1: String,
    val iso_639_1: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val published_at: String,
    val site: String,
    val size: Int,
    val type: String
)