package com.dejvidleka.moviehub_jetpackcompose.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dejvidleka.moviehub_jetpackcompose.ui.components.MetallicTitle
import com.dejvidleka.moviehub_jetpackcompose.ui.screens.home.components.CarouselSection
import com.dejvidleka.moviehub_jetpackcompose.ui.screens.home.components.CategorySection
import com.dejvidleka.moviehub_jetpackcompose.ui.screens.home.components.MovieCategory
import com.dejvidleka.moviehub_jetpackcompose.ui.screens.home.components.MovieSection
import com.dejvidleka.moviehub_jetpackcompose.ui.viewmodels.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val virtualItemCount = Int.MAX_VALUE
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val startIndex = virtualItemCount / 2
    val pagerState = rememberPagerState(initialPage = startIndex, pageCount = { virtualItemCount })
    val swiperRefreshState = rememberSwipeRefreshState(isRefreshing)

    val categories = listOf(
        MovieCategory(
            "Trending Movies",
            viewModel.getMovieState(MovieType.POPULAR).collectAsState(), isCarousel = true
        ),
        MovieCategory(
            "Now Playing",
            viewModel.getMovieState(MovieType.NOW_PLAYING).collectAsState()
        ),
        MovieCategory(
            "Popular Movies",
            viewModel.getMovieState(MovieType.POPULAR).collectAsState()
        ),
        MovieCategory(
            "Top Rated Movies",
            viewModel.getMovieState(MovieType.TOP_RATED).collectAsState()
        ),
        MovieCategory(
            "Upcoming Movies",
            viewModel.getMovieState(MovieType.UPCOMING).collectAsState()
        )
    )

    Scaffold(topBar = {}) {
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
                    categories.forEach { category ->
                        item(key = category.title) {
                            val movieState = category.movieState.value

                            if (category.isCarousel) {
                                MovieSection(
                                    movieState = movieState,
                                    onRetry = viewModel::retry
                                ) { movies ->
                                    CarouselSection(
                                        pagerState = pagerState,
                                        actualItemCount = movies.results.size,
                                        movies = movies
                                    )
                                }
                            } else {
                                CategorySection(
                                    title = category.title,
                                    movies = movieState,
                                    viewModel = viewModel
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}




