package com.dejvidleka.moviehub_jetpackcompose.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.PopularMovies
import kotlin.math.absoluteValue


@Composable
fun CarouselSection(
    pagerState: PagerState,
    actualItemCount: Int,
    movies: PopularMovies
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 80.dp),
            pageSpacing = 16.dp
        ) { page ->
            val actualIndex = page.mod(actualItemCount)
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        lerp(
                            start = 0.8f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .fillMaxWidth()
            ) {
                HeroItem(
                    movies.results[actualIndex],
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}