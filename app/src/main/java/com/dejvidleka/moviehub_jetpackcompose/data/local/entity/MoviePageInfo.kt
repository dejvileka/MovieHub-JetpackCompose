package com.dejvidleka.moviehub_jetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_page_info")
data class MoviePageInfoEntity(
    @PrimaryKey
    val type: String, // MovieType as string serves as primary key
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    val timestamp: Long
)