package com.dejvidleka.moviehub_jetpackcompose.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieResponse
import com.dejvidleka.moviehub_jetpackcompose.data.result.Result
import com.dejvidleka.moviehub_jetpackcompose.ui.viewmodels.HomeViewModel

@Composable
fun CategorySection(
    title: String,
    movies: Result<MovieResponse>,
    viewModel: HomeViewModel
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        elevation = 10.dp,
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Column(
            Modifier
                .height(300.dp)
                .padding(bottom = 10.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(movies.getOrNull()?.results ?: emptyList()) { movie ->
                    val isFavorite by viewModel.isMovieFavorite(movie.id).collectAsState()
                    Card(
                        modifier = Modifier.width(135.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8F)
                        ),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            HeroItem(
                                movie = movie,
                                modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth()
                                    .weight(1F),
                                isFavorite = isFavorite, onClick = {
                                    viewModel.toggleFavorite(movie)
                                }
                            )
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .padding(6.dp)
                                    .height(50.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}