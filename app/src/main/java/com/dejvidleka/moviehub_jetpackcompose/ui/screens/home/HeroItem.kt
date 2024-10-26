package com.dejvidleka.moviehub_jetpackcompose.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieDetails


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HeroItem(
    movie: MovieDetails,
    modifier: Modifier
) {
    val baseImageUrl = "https://image.tmdb.org/t/p/original"

    Box(
        modifier = modifier
            .aspectRatio(5f / 7f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                model = baseImageUrl + movie.poster_path,
                contentDescription = "Movie poster",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}