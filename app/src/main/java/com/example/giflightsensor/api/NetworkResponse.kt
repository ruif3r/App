package com.example.giflightsensor.api

sealed class NetworkResponse<T>(val data : T? = null, val error : Exception? = null) {

    class Success<T>(data: T) : NetworkResponse<T>(data)
    class Error<T>(error: Exception) : NetworkResponse<T>(null, error)
}