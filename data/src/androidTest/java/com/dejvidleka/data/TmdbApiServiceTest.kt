package com.dejvidleka.data

import com.dejvidleka.data.remote.TMDBAPITService
import com.dejvidleka.data.remote.models.MovieByGenre
import com.dejvidleka.data.remote.models.MovieResult
import com.dejvidleka.data.remote.models.TopRatedMovies
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response


class TmdbApiServiceTest {
    @Test
    fun get_top_rated_movies_returns_successful_response() = runBlocking {
        val mockApiService = mock(TMDBAPITService::class.java)
        val expectedResponse = TopRatedMovies(
            page = 1, movieResults = listOf(
                MovieResult(
                    false,
                    backdrop_path = "",
                    genre_ids = listOf(1, 2, 3, 4),
                    id = 23432,
                    original_language = "en",
                    original_title = "sample",
                    overview = "overvieww....",
                    popularity = 10.3,
                    poster_path = "poster",
                    release_date = "22/22/22",
                    title = "sample title",
                    video = false,
                    vote_average = 8.3,
                    vote_count = 432344,
                    isViewMore = true
                )
            ), total_results = 1, total_pages = 34
        )
        `when`(mockApiService.getTopRatedMovies("7455ea1ca9a31d9965a3176d4cab72e3", 1))
            .thenReturn(Response.success(expectedResponse))

        val actualResponse = mockApiService.getTopRatedMovies("7455ea1ca9a31d9965a3176d4cab72e3", 1)

        assertTrue(actualResponse.isSuccessful)
        assertEquals(expectedResponse, actualResponse.body())
    }
    @Test
    fun get_trending_movies_returns_successful_response1() = runBlocking {
        val mockApiService = mock(TMDBAPITService::class.java)
        val expectedResponse = MovieByGenre(
            page = 1, movieResults = listOf(
                MovieResult(
                    false,
                    backdrop_path = "",
                    genre_ids = listOf(1, 2, 3, 4),
                    id = 23432,
                    original_language = "en",
                    original_title = "sample",
                    overview = "overvieww....",
                    popularity = 10.3,
                    poster_path = "poster",
                    release_date = "22/22/22",
                    title = "sample title",
                    video = false,
                    vote_average = 8.3,
                    vote_count = 432344,
                    isViewMore = true
                )
            ), total_results = 1, total_pages = 34
        )
        `when`(mockApiService.getTrendingMovies("7455ea1ca9a31d9965a3176d4cab72e3"))
            .thenReturn(Response.success(expectedResponse))

        val actualResponse = mockApiService.getTrendingMovies("7455ea1ca9a31d9965a3176d4cab72e3")

        assertTrue(actualResponse.isSuccessful)
        assertEquals(expectedResponse, actualResponse.body())
    }
    @Test
    fun getPopularMovies_returns_successful_response2() = runBlocking {
        val mockApiService = mock(TMDBAPITService::class.java)
        val expectedResponse = TopRatedMovies(
            page = 1, movieResults = listOf(
                MovieResult(
                    false,
                    backdrop_path = "",
                    genre_ids = listOf(1, 2, 3, 4),
                    id = 23432,
                    original_language = "en",
                    original_title = "sample",
                    overview = "overvieww....",
                    popularity = 10.3,
                    poster_path = "poster",
                    release_date = "22/22/22",
                    title = "sample title",
                    video = false,
                    vote_average = 8.3,
                    vote_count = 432344,
                    isViewMore = true
                )
            ), total_results = 1, total_pages = 34
        )
        `when`(mockApiService.getTopRatedMovies("7455ea1ca9a31d9965a3176d4cab72e3", 1))
            .thenReturn(Response.success(expectedResponse))

        val actualResponse = mockApiService.getTopRatedMovies("7455ea1ca9a31d9965a3176d4cab72e3", 1)

        assertTrue(actualResponse.isSuccessful)
        assertEquals(expectedResponse, actualResponse.body())
    }
}
