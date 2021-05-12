package com.kido1611.dicoding.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import com.kido1611.dicoding.moviecatalogue.model.Movie

interface MovieDataSource {
    fun getDiscoverMovie(): LiveData<UIState<List<Movie>>>
    fun getDiscoverTv(): LiveData<UIState<List<Movie>>>

    fun getMovieById(id: Int): LiveData<UIState<Movie>>
    fun getTvById(id: Int): LiveData<UIState<Movie>>
}

sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    data class Error(val message: String) : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
}
