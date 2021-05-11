package com.kido1611.dicoding.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import com.kido1611.dicoding.moviecatalogue.model.Movie

interface MovieDataSource {
    fun getDiscoverMovie(): LiveData<DiscoverUiState>
    fun getDiscoverTv(): LiveData<DiscoverUiState>

    fun getMovieById(id: Int): LiveData<DetailUiState>
    fun getTvById(id: Int): LiveData<DetailUiState>
}

sealed class DiscoverUiState {
    data class Loading(val list: List<Movie>?) : DiscoverUiState()
    data class Success(val list: List<Movie>) : DiscoverUiState()
    data class Error(val message: String) : DiscoverUiState()
}

sealed class DetailUiState {
    data class Loading(val movie: Movie?) : DetailUiState()
    data class Success(val movie: Movie) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}
