package com.dejvidleka.moviehub_jetpackcompose.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieDetails


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HeroItem(
    movie: MovieDetails,
    modifier: Modifier,
    showSave: Boolean = true,
    isFavorite: Boolean = false,
    onClick: () -> Unit
) {
    val baseImageUrl = "https://image.tmdb.org/t/p/original"

    Box(
        modifier = modifier
            .aspectRatio(6f / 8f)
    ) {
        Surface (
            modifier = modifier.aspectRatio(6f / 8f),
            shape = RoundedCornerShape(8.dp),
            tonalElevation = 4.dp,
            shadowElevation = 4.dp,
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
        if (showSave) {
            Surface(
                color = Color(0xFF121212).copy(alpha = 0.5F),
                modifier = Modifier
                    .padding(1.dp)
                    .clickable(onClick = onClick)
                    .align(Alignment.TopStart)
                    .size(36.dp)
                    .clip(RoundedCornerShape(topStart = 3.dp))
            ) {
                if (!isFavorite) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "",
                        tint = Color.White.copy(alpha = 0.8F),
                        modifier = Modifier
                            .size(24.dp)
                            .graphicsLayer(
                                scaleX = 0.6f,
                                scaleY = 0.6f
                            )
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "",
                        tint = Color.Yellow.copy(alpha = 0.8F),
                        modifier = Modifier
                            .size(24.dp)
                            .graphicsLayer(
                                scaleX = 0.6f,
                                scaleY = 0.6f
                            )
                    )
                }
            }
        }
    }
}
