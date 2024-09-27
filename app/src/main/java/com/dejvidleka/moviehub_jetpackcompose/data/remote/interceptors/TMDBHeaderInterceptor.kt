package com.dejvidleka.moviehub_jetpackcompose.data.remote.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class TMDBHeaderInterceptor(private val apiKey: String) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
        Log.d("Url", "Final Request URL: $newUrl")
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}


