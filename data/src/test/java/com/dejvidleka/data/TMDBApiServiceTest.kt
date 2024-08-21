package com.dejvidleka.data

import com.dejvidleka.data.remote.TMDBApiService
import com.dejvidleka.data.remote.models.Cast
import com.dejvidleka.data.remote.models.Crew
import com.dejvidleka.data.remote.models.MovieByGenre
import com.dejvidleka.data.remote.models.MovieCast
import com.dejvidleka.data.remote.models.MovieResult
import com.dejvidleka.data.remote.models.TopRatedMovies
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException


class TMDBApiServiceTest {
    private val mockApiService: TMDBApiService = mock(TMDBApiService::class.java)

//    @Test
//    fun get_top_rated_movies_returns_successful_response() = runBlocking {
//        val expectedResponse = TopRatedMovies(
//            page = 1, movieResults = listOf(
//                MovieResult(
//                    false,
//                    backdrop_path = "",
//                    genre_ids = listOf(1, 2, 3, 4),
//                    id = 23432,
//                    original_language = "en",
//                    original_title = "sample",
//                    overview = "overvieww....",
//                    popularity = 10.3,
//                    poster_path = "poster",
//                    release_date = "22/22/22",
//                    title = "sample title",
//                    video = false,
//                    vote_average = 8.3,
//                    vote_count = 432344,
//                    isViewMore = true
//                )
//            ), total_results = 1, total_pages = 34
//        )
//        `when`(mockApiService.getTopRatedMovies(BuildConfig.API_KEY, 1))
//            .thenReturn(Response.success(expectedResponse))
//
//        val actualResponse = mockApiService.getTopRatedMovies(BuildConfig.API_KEY, 1)
//
//        assertTrue(actualResponse.isSuccessful)
//        assertEquals(expectedResponse, actualResponse.body())
//    }
    @Test
    fun get_trending_movies_returns_successful_response() = runBlocking {
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
        `when`(mockApiService.getTrendingMovies(BuildConfig.API_KEY))
            .thenReturn(Response.success(expectedResponse))

        val actualResponse = mockApiService.getTrendingMovies(BuildConfig.API_KEY)

        assertTrue(actualResponse.isSuccessful)
        assertEquals(expectedResponse, actualResponse.body())
    }

    @Test
    fun get_movie_cast_bring_successful_response() = runBlocking {
        val expectedResponse = MovieCast(
            id = 1, cast = listOf(
                Cast(
                    adult = true, cast_id = 23, character = "vrw", gender = 1, id = 4, name = "csd", original_name = "dcas", profile_path = "cdsw"
                )
            ), crew = listOf(
                Crew(
                    name = "dcdsw",
                    gender = 1,
                    credit_id = "cdsa",
                    job = "dsa",
                    profile_path = "dqsw",
                    id = 232,
                    adult = true,
                    original_name = "23e2",
                )
            )
        )
        `when`(mockApiService.getMovieCast(movieId = 1423431241, BuildConfig.API_KEY)).thenReturn(Response.success(expectedResponse))
        val actualResponse = mockApiService.getMovieCast(1423431241, BuildConfig.API_KEY)
        assertTrue(actualResponse.isSuccessful)
        assertEquals(expectedResponse, actualResponse.body())
    }

    @Test
    fun getMoviesByGenre_handles_error_response() = runBlocking {
        val errorResponse = HttpException(Response.error<MovieByGenre>(401, "".toResponseBody()))
        `when`(mockApiService.getMoviesByGenre(BuildConfig.API_KEY, "", "", 2)).thenAnswer {
            throw CancellationException("API call canceled", errorResponse)
        }
        try {
            mockApiService.getMoviesByGenre(BuildConfig.API_KEY, "", "", 2)
            fail("should throw CancellationException")
        } catch (e: CancellationException) {
            val cause = e.cause
            assertTrue(cause is HttpException)
            assertEquals(401, (cause as HttpException).code())
        }
    }
}

//@Test
//fun getMoviesByGenre_withValidParams_returnsSuccessfulResponse() {
//
//
//}


fun getMoviesByGenre_withInvalidApiKey_returnsErrorResponse() {

}
fun getMoviesByGenre_withInvalidGenre_returnsErrorResponse() {}
fun getMoviesByGenre_withNoResults_returnsEmptyResponse() {}
//fun getTopRatedMovies() {}

fun getTopRatedMovies_withValidPage_returnsSuccessfulResponse() {}
fun getTopRatedMovies_withInvalidApiKey_returnsErrorResponse() {}
fun getTopRatedMovies_withInvalidPage_returnsErrorResponse() {}
//fun getTrendingMovies() {}

fun getTrendingMovies_returnsSuccessfulResponse() {}
fun getTrendingMovies_withInvalidApiKey_returnsErrorResponse() {}
//fun getMovieCast() {}

fun getMovieCast_withValidMovieId_returnsSuccessfulResponse() {}
fun getMovieCast_withInvalidMovieId_returnsErrorResponse() {}
fun getMovieCast_withInvalidApiKey_returnsErrorResponse() {}
//fun getMovieDetails() {}

fun getMovieDetails_withValidMovieId_returnsSuccessfulResponse() {}
fun getMovieDetails_withInvalidMovieId_returnsErrorResponse() {}
fun getMovieDetails_withInvalidApiKey_returnsErrorResponse() {}
//fun getTvDetails() {}

fun getTvDetails_withValidTvId_returnsSuccessfulResponse() {}
fun getTvDetails_withInvalidTvId_returnsErrorResponse() {}
fun getTvDetails_withInvalidApiKey_returnsErrorResponse() {}
//fun getMovieTrailer() {}

fun getMovieTrailer_withValidMovieId_returnsSuccessfulResponse() {}
fun getMovieTrailer_withInvalidMovieId_returnsErrorResponse() {}
fun getMovieTrailer_withInvalidApiKey_returnsErrorResponse() {}
//fun getMovieGenres() {}

fun getMovieGenres_returnsSuccessfulResponse() {}
fun getMovieGenres_withInvalidApiKey_returnsErrorResponse() {}
//fun getSimilarMovies() {}

fun getSimilarMovies_withValidMovieId_returnsSuccessfulResponse() {}
fun getSimilarMovies_withInvalidMovieId_returnsErrorResponse() {}
fun getSimilarMovies_withInvalidApiKey_returnsErrorResponse() {}
//fun searchMovies() {}

fun searchMovies_withValidQuery_returnsSuccessfulResponse() {}
fun searchMovies_withEmptyQuery_returnsErrorResponseOrEmptyResponse() {}
fun searchMovies_withInvalidApiKey_returnsErrorResponse() {}
//fun getMovieProviders() {}

fun getMovieProviders_withValidMovieId_returnsSuccessfulResponse() {}
fun getMovieProviders_withInvalidMovieId_returnsErrorResponse() {}
fun getMovieProviders_withInvalidApiKey_returnsErrorResponse() {}
//fun getProviderRegions() {}

fun getProviderRegions_returnsSuccessfulResponse() {}
fun getProviderRegions_withInvalidApiKey_returnsErrorResponse() {}
//fun getMovieProvidersNames() {}

fun getMovieProvidersNames_returnsSuccessfulResponse() {}
fun getMovieProvidersNames_withInvalidApiKey_returnsErrorResponse() {}