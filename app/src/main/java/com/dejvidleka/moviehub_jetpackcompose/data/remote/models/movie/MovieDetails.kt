package com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie

import com.dejvidleka.moviehub_jetpackcompose.data.local.entity.MovieDetailsEntity

data class MovieDetails(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun MovieDetails.toMovieDetails(): MovieDetailsEntity {
    return MovieDetailsEntity(
        id = this.id,
        adult = this.adult,
        backdrop_path = this.backdrop_path,
        original_language = this.original_language,
        original_title = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        poster_path = this.poster_path,
        release_date = this.release_date,
        title = this.title,
        video = this.video,
        vote_average = this.vote_average,
        vote_count = this.vote_count,
        genre_ids = this.genre_ids,
    )
}