package com.example.cryptocurrency.data.model


sealed class ResponseState<out T> {
    data class Success<out T>(val data: T) : ResponseState<T>()
    data class Error(val exception: String) : ResponseState<Any?>()
}
