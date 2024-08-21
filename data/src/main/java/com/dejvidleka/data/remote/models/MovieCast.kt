package com.dejvidleka.data.remote.models

data class MovieCast(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)

data class Cast(
    val adult: Boolean,
    val cast_id: Int,
    val character: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val original_name: String,
    val profile_path: String
)

data class Crew(
    val adult: Boolean,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val name: String,
    val original_name: String,
    val profile_path: String
)
