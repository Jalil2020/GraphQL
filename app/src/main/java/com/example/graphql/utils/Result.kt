package com.example.graphql.utils

sealed class Result<T>() {
    class Success<T>(val data: T) : Result<T>()
    class Error<T>(val message: String) : Result<T>()
    class Loading<T>( val isLoading: Boolean) : Result<T>()
}

enum class ApiStatus {
    SUCCESS,
    ERROR,
    LOADING
}
