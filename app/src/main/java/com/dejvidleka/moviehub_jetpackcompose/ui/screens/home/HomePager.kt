package com.dejvidleka.moviehub_jetpackcompose.ui.screens.home

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.dejvidleka.moviehub_jetpackcompose.data.remote.models.movie.MovieResponse
import kotlin.math.absoluteValue


@Composable
fun CarouselSection(
    pagerState: PagerState,
    actualItemCount: Int,
    movies: MovieResponse
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier,
            contentPadding = PaddingValues(horizontal = 40.dp),
            pageSize = PageSize.Fill,
            beyondViewportPageCount = 2,  // Keep 2 pages loaded beyond viewport for smooth scrolling
            pageSpacing = (-25).dp,
            verticalAlignment = Alignment.CenterVertically,  // Center pages vertically
            flingBehavior = PagerDefaults.flingBehavior(
                state = pagerState,
                snapAnimationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),  // Smooth fling animation with slight bounce
            userScrollEnabled = true,  // Allow user scrolling
            reverseLayout = false,  // Standard left-to-right layout
            key = null,  // No specific key needed unless you're dynamically changing pages
            pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                state = pagerState,
                orientation = Orientation.Horizontal
            ),  // Default nested scroll behavior
            snapPosition = SnapPosition.Start  // Snap pages to center alignment
        ) { page ->
            val actualIndex = page.mod(actualItemCount)
            Box(
                modifier = Modifier.padding(top = 10.dp)
                    .fillMaxWidth()
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