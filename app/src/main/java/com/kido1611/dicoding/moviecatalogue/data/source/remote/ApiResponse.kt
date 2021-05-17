package com.kido1611.dicoding.moviecatalogue.data.source.remote

sealed class ApiResponse<out T> {
    object Loading : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
    data class Error(val message: String) : ApiResponse<Nothing>()
    data class Success<T>(val data: T) : ApiResponse<T>()
}