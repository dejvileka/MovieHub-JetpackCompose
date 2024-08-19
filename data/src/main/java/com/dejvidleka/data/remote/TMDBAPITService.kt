package com.dejvidleka.data.remote

import com.dejvidleka.data.remote.models.Countries
import com.dejvidleka.data.remote.models.GenreResponse
import com.dejvidleka.data.remote.models.MovieByGenre
import com.dejvidleka.data.remote.models.MovieCast
import com.dejvidleka.data.remote.models.MovieDetails
import com.dejvidleka.data.remote.models.ProvidersName
import com.dejvidleka.data.remote.models.ProvidersResponse
import com.dejvidleka.data.remote.models.SearchResultMovies
import com.dejvidleka.data.remote.models.SimilarMovies
import com.dejvidleka.data.remote.models.TopRatedMovies
import com.dejvidleka.data.remote.models.Trailer
import com.dejvidleka.data.remote.models.TvDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TMDBAPITService {

    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("without_keywords") withoutKeywords: String,
        @Query("with_genres") genre: String,
        @Query("page") page: Int
    ): Response<MovieByGenre>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<TopRatedMovies>

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String
    ): Response<MovieByGenre>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieCast>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieDetails>

    @GET("tv/{tv_id}")
    suspend fun getTvDetails(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String
    ): Response<TvDetails>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailer(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<Trailer>

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String
    ): Response<GenreResponse>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<SimilarMovies>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Response<SearchResultMovies>

    @GET("movie/{movie_id}/watch/providers")
    suspend fun getMovieProviders(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<ProvidersResponse>


    @GET("watch/providers/regions")
    suspend fun getProviderRegions(
        @Query("api_key") apiKey: String
    ): Response<Countries>

    @GET("watch/providers/movie")
    suspend fun getMovieProvidersNames(
        @Query("api_key") apiKey: String
    ): Response<ProvidersName>
}