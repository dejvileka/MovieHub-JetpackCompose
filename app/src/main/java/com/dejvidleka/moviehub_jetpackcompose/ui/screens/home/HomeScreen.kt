package com.dejvidleka.moviehub_jetpackcompose.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dejvidleka.moviehub_jetpackcompose.data.remote.Result
import com.dejvidleka.moviehub_jetpackcompose.ui.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val popularUiState by viewModel.popularMovies.collectAsState()
    val pagerState = rememberPagerState(pageCount = { 10 })
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text("Kino Hub") })
    }) { paddingValues ->


        LazyColumn(Modifier.padding(paddingValues)) {
            item {
                when (val uiState = popularUiState) {
                    is Result.Success -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            HorizontalPager(
                                state = pagerState, modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            ) { page ->
                                HeroItem(uiState.data.results[page], modifier = Modifier)
                            }
                            Row(
                                Modifier
                                    .height(50.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                repeat(pagerState.pageCount) { iteration ->
                                    val color =
                                        if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                                            alpha = 0.5f
                                        )
                                    Box(
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(color)
                                            .size(8.dp)
                                    )
                                }
                            }
                        }
                    }


                    is Result.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is Result.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Error: ")
                        }
                    }
                }
            }
        }
    }
}