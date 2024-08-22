package com.dejvidleka.data.di

import com.dejvidleka.data.remote.TMDBApiService
import com.dejvidleka.data.repositories.APIKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHTTPClient(): OkHttpClient {
        val cacheDir = File("cacheDir", "okhttpcache")
        val appCache = Cache(cacheDir, 10 * 1024 * 1024)
        val bootstrapClient = OkHttpClient.Builder().cache(appCache).build()
        val dns = DnsOverHttps.Builder().client(bootstrapClient)
            .url("https://1.1.1.1/dns-query".toHttpUrl())
            .build()
        return bootstrapClient.newBuilder()
            .dns(dns)
            .addInterceptor(APIKeyInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieServices(retrofit: Retrofit): TMDBApiService {
        return retrofit.create(TMDBApiService::class.java)
    }
}