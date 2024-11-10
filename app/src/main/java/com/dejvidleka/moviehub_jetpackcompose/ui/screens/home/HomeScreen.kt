package com.dejvidleka.moviehub_jetpackcompose.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieResponse
import com.dejvidleka.moviehub_jetpackcompose.data.result.Result
import com.dejvidleka.moviehub_jetpackcompose.ui.viewmodels.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val popularMovies by viewModel.getMovieState(MovieType.POPULAR).collectAsState()
    val topMovies by viewModel.getMovieState(MovieType.TOP_RATED).collectAsState()
    val nowPlayingMovies by viewModel.getMovieState(MovieType.NOW_PLAYING).collectAsState()
    val upcomingMovies by viewModel.getMovieState(MovieType.UPCOMING).collectAsState()
    val virtualItemCount = Int.MAX_VALUE
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val actualItemCount = 20
    val startIndex = virtualItemCount / 2
    val pagerState = rememberPagerState(initialPage = startIndex, pageCount = { virtualItemCount })
    val swiperRefreshState = rememberSwipeRefreshState(isRefreshing)

    Scaffold(topBar = {

    }) { paddingValues ->
        SwipeRefresh(
            modifier = Modifier.padding(paddingValues),
            state = swiperRefreshState,
            onRefresh = { viewModel.refresh() },
            swipeEnabled = true,
            refreshTriggerDistance = 100.dp,
            indicatorPadding = PaddingValues(top = 10.dp),
            clipIndicatorToPadding = true,
            content = {
                LazyColumn {
                    item {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background.copy(
                                    alpha = 0.1F
                                )
                            ),
                            title = {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Kino Hub",
                                        style = MaterialTheme.typography.titleLarge,
                                    )
                                }
                            },
                        )
                    }
                    item {
                        MovieSection(
                            movieState = popularMovies, onRetry = viewModel::retry
                        ) { movies ->
                            CarouselSection(
                                pagerState = pagerState,
                                actualItemCount = actualItemCount,
                                movies = movies
                            )
                        }
                    }
                    item {
                        CategorySection(title = "Recomended", movies = topMovies)
                    }
                }
            }
        )
    }
}

@Composable
fun CategorySection(
    title: String,
    movies: Result<MovieResponse>
) {
    Column(Modifier.height(300.dp)) {
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
                HeroItem(movie = movie, modifier = Modifier)
            }
        }
    }
}

