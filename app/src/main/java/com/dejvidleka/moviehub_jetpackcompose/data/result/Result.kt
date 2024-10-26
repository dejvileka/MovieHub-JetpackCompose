package com.dejvidleka.moviehub_jetpackcompose.data.result


sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val message: String, val throwable: Throwable? = null) : Result<Nothing>
    data object Loading: Result<Nothing>

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isError() = this is Error

    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }
}




