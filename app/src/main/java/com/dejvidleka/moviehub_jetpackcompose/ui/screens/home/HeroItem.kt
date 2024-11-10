package com.dejvidleka.moviehub_jetpackcompose.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
            .aspectRatio(6f / 8f)
    ) {
        Surface (
            modifier = modifier.aspectRatio(6f / 8f),
            shape = RoundedCornerShape(8.dp),
            tonalElevation = 8.dp,
            shadowElevation = 8.dp,
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 12f)
            )
        ) {
            GlideImage(
                model = baseImageUrl + movie.poster_path,
                contentDescription = "Movie poster",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(3.dp)),
                contentScale = ContentScale.FillBounds
            )

    }
    }
}
