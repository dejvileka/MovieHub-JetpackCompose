package com.dejvidleka.data.repositories

import com.dejvidleka.data.remote.models.MovieResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getTopRated(): Flow<List<MovieResult>>
}