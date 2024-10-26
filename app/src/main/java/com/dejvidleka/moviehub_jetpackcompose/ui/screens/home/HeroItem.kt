package com.dejvidleka.moviehub_jetpackcompose.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieDetails


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HeroItem(movie: MovieDetails,modifier: Modifier) {
    val baseImageUrl =
        "https://image.tmdb.org/t/p/w500"
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard(
            modifier = modifier.padding(10.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            GlideImage(
                model = baseImageUrl + movie.poster_path,
                contentDescription = "Movie poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(RoundedCornerShape(8.dp)).fillMaxSize()
            )
        }
        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}