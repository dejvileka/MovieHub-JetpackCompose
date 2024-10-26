package com.dejvidleka.moviehub_jetpackcompose.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieDetails
import com.dejvidleka.moviehub_jetpackcompose.ui.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val popularUiState by viewModel.popularMovies.collectAsState()
    val virtualItemCount = Int.MAX_VALUE
    val actualItemCount = 20
    val startIndex = virtualItemCount / 2
    val pagerState = rememberPagerState(initialPage = startIndex, pageCount = { virtualItemCount })
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text("Kino Hub") })
    }) { paddingValues ->
        LazyColumn(Modifier.padding(paddingValues)) {
            item {
                MovieSection(
                    movieState = popularUiState,
                    onRetry = viewModel::retry
                ) { movies ->
                    CarouselSection(
                        pagerState = pagerState,
                        actualItemCount = actualItemCount,
                        movies = movies
                    )
                }
            }
        }
    }
}

@Composable
fun CategorySection(
    title: String, movies: List<MovieDetails>
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movies) { movie ->
                HeroItem(movie = movie, modifier = Modifier)
            }
        }
    }
}

