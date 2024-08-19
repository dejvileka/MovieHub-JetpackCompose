package com.dejvidleka.data.remote.models

data class Countries(
    val results: List<Regions>
)

data class Regions(
    val englishName: String,
    val iso: String,
    val nativeName: String
)