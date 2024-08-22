package com.dejvidleka.data.repositories

import com.dejvidleka.data.remote.models.MovieResult
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton


interface MovieRepository {
    fun getTopRated(): Flow<List<MovieResult>>
}