package com.dejvidleka.moviehub_jetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MOVIES")
data class MovieDetailsEntity(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val type: String,
    val timeStamp: Long
)

