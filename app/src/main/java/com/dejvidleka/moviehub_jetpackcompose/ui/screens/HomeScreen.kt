package com.dejvidleka.moviehub_jetpackcompose.ui.screens

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.dejvidleka.data.di.Result
import com.dejvidleka.data.remote.models.MovieResult
import com.dejvidleka.moviehub_jetpackcompose.ui.viewmodels.HomeViewModel

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()

) {
    val uiState by viewModel.uiState.collectAsState()
    val horizontalState = rememberPagerState(initialPage = 0, pageCount = { 20 })

    Scaffold(contentWindowInsets = WindowInsets(0.dp), topBar = {
        CenterAlignedTopAppBar(title = {
            Text(text = "KinoHUB", style = MaterialTheme.typography.titleLarge)
        }, navigationIcon = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "")
            }
        }

        )
    }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            item {
            when (uiState) {
                is Result.Success -> {
                    HorizontalPager(
                        state = horizontalState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = 32.dp
                            ),
                        pageSpacing = 1.dp,
                        beyondViewportPageCount = 9
                    ) { page ->
                        val movie = (uiState as Result.Success<List<MovieResult>>).data[page]
                        Column {
                            Box(modifier = Modifier
                                .zIndex(if (page == horizontalState.currentPage) 1f else 0f)
                                .padding(
                                    start = 20.dp,
                                    end = 20.dp,
                                )
                                .graphicsLayer {
                                    val pageOffset = horizontalState.getOffsetDistanceInPages(page)

                                    val startOffset = when (page) {
                                        horizontalState.currentPage -> -pageOffset
                                        // Current page moves to the right
                                        horizontalState.currentPage + 1 ->  pageOffset
                                        // Next page moves from the right
                                        horizontalState.currentPage - 1 -> -pageOffset
                                        // Previous page moves from the left
                                        else -> 1f
                                        // Other pages are off-screen
                                    }

//                                    val startOffset = horizontalState.currentPageOffsetFraction

                                    translationX = size.width * (startOffset * .4f)
                                    alpha = (2f - startOffset) / 2f

                                    val blur = (startOffset * 20f).coerceAtLeast(0.5f)
                                    renderEffect = RenderEffect
                                        .createBlurEffect(
                                            blur, blur, Shader.TileMode.DECAL
                                        )
                                        .asComposeRenderEffect()

                                    val scale = 1f - (startOffset * .1f)
                                    scaleX = scale
                                    scaleY = scale
                                }
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    color = Color(0xFFF58133), shape = RoundedCornerShape(20.dp)
                                ), contentAlignment = Alignment.Center

                            ) {
                                AsyncImage(
                                    contentScale = ContentScale.Crop,
                                    model = "https://image.tmdb.org/t/p/w500/${movie.poster_path}",
                                    contentDescription = "Movie Poster",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {

                                val verticalState = rememberPagerState(pageCount = { 10 })

                                VerticalPager(
                                    state = verticalState,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(72.dp),
                                    userScrollEnabled = false,
                                    horizontalAlignment = Alignment.Start,
                                ) { page ->
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                    ) {
                                        movie.title?.let {
                                            Text(
                                                text = it, style = MaterialTheme.typography.bodySmall.copy(
                                                    fontWeight = FontWeight.Thin,
                                                    fontSize = 28.sp,
                                                )
                                            )
                                        }
                                        movie.overview?.let {
                                            Text(
                                                text = it, style = MaterialTheme.typography.bodySmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp,
                                                    color = Color.Black.copy(alpha = .56f),
                                                )
                                            )
                                        }
                                    }


                                    LaunchedEffect(Unit) {
                                        snapshotFlow {
                                            Pair(
                                                horizontalState.currentPage, horizontalState.currentPageOffsetFraction
                                            )
                                        }.collect { (page, offset) ->
                                            verticalState.scrollToPage(page, offset)
                                        }
                                    }
                                }


                                /*               val interpolatedRating by remember {
                                                   derivedStateOf {
                                                       val position = verticalState.currentPageOffsetFraction
                                                       val from = floor(position).roundToInt()
                                                       val to = ceil(position).roundToInt()

                                                       val fromRating = movies[from].rating.toFloat()
                                                       val toRating = movies[to].rating.toFloat()

                                                       val fraction = position - position.toInt()
                                                       fromRating + ((toRating - fromRating) * fraction)
                                                   }
                                               }*/

                            }
                        }
                    }
                }
                is Result.Loading -> {
                    Log.d("movie here", "Loading")

                }
                is Result.Error -> {
                    Log.d("movie here", "error")
                }
            }
        }
        }
    }
}
