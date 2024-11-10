package com.dejvidleka.moviehub_jetpackcompose.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieResponse
import com.dejvidleka.moviehub_jetpackcompose.data.result.Result
import com.dejvidleka.moviehub_jetpackcompose.ui.components.MetallicTitle
import com.dejvidleka.moviehub_jetpackcompose.ui.viewmodels.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val popularMovies by viewModel.getMovieState(MovieType.POPULAR).collectAsState()
    val topMovies by viewModel.getMovieState(MovieType.TOP_RATED).collectAsState()
    val nowPlayingMovies by viewModel.getMovieState(MovieType.NOW_PLAYING).collectAsState()
    val upcomingMovies by viewModel.getMovieState(MovieType.UPCOMING).collectAsState()
    val trendingMovies by viewModel.getMovieState(MovieType.TRENDING).collectAsState()
    val virtualItemCount = Int.MAX_VALUE
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val actualItemCount = 20
    val startIndex = virtualItemCount / 2
    val pagerState = rememberPagerState(initialPage = startIndex, pageCount = { virtualItemCount })
    val swiperRefreshState = rememberSwipeRefreshState(isRefreshing)


    Scaffold(topBar = {})
    {
        SwipeRefresh(
            modifier = Modifier.padding(),
            state = swiperRefreshState,
            onRefresh = { viewModel.refresh() },
            swipeEnabled = true,
            clipIndicatorToPadding = true,
            content = {
                LazyColumn {
                    item {
                        CenterAlignedTopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background.copy(
                                    alpha = 0.1F
                                )
                            ),
                            title = {
                                MetallicTitle()
                            },
                        )
                    }
                    item {
                        MovieSection(
                            movieState = trendingMovies,
                            onRetry = viewModel::retry
                        ) { movies ->
                            CarouselSection(
                                pagerState = pagerState,
                                actualItemCount = actualItemCount,
                                movies = movies
                            )
                        }
                    }
                    item {
                        CategorySection(
                            title = "Popular Movies", movies = popularMovies, viewModel = viewModel
                        )
                    }
                    item {
                        CategorySection(
                            title = "Top Rated Movies", movies = topMovies, viewModel = viewModel
                        )
                    }
                    item {
                        CategorySection(
                            title = "Upcoming Movies",
                            movies = upcomingMovies,
                            viewModel = viewModel
                        )
                    }
                }
            }
        )
    }
}

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

