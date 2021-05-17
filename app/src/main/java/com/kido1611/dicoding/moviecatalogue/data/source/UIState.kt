package com.kido1611.dicoding.moviecatalogue.data.source

sealed class UIState<out T> {
    data class Loading<T>(val data: T?) : UIState<T>()
    data class Error<T>(val message: String, val data: T? = null) : UIState<T>()
    data class Success<T>(val data: T) : UIState<T>()
}