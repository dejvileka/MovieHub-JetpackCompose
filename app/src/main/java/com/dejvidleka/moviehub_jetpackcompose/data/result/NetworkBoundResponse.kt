package com.dejvidleka.moviehub_jetpackcompose.data.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class NetworkBoundResponse<T>(
    private val fetchFromNetwork: suspend () -> T,
    private val shouldFetch: (T?) -> Boolean = { true },
    private val saveLocally: suspend (T) -> Unit = {},
    private val getLocally: suspend () -> T? = { null }
) {
    fun asFlow(): Flow<Result<T>> = flow {
        emit(Result.Loading)

        getLocally()?.let { cachedData ->
            emit(Result.Success(cachedData))
            if (!shouldFetch(cachedData)) {
                return@flow
            }
        }
        try {
            val networkResult = fetchFromNetwork()
            saveLocally(networkResult)
            emit(Result.Success(networkResult))
        } catch (e: Exception) {
            emit(
                Result.Error(
                    message = e.localizedMessage ?: "Unknown error occurred",
                    throwable = e
                )
            )
        }

    }.catch { e ->
        emit(
            Result.Error(
                message = e.localizedMessage ?: "Unknown error occurred",
                throwable = e
            )
        )
    }
}
