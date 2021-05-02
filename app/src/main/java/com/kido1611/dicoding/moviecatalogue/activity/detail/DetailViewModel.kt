package com.kido1611.dicoding.moviecatalogue.activity.detail

import androidx.lifecycle.ViewModel
import com.kido1611.dicoding.moviecatalogue.model.Movie
import com.kido1611.dicoding.moviecatalogue.utils.DataDummy

class DetailViewModel : ViewModel() {

    private var _isMovie: Boolean = true
    private var _movieId: Int = 0

    fun setMovie(isMovie: Boolean, movieId: Int) {
        _isMovie = isMovie
        _movieId = movieId
    }

    fun getMovie(): Movie? {
        val movies = if (_isMovie) {
            DataDummy.generateDummyMovies()
        } else {
            DataDummy.generateDummyTv()
        }

        return movies.firstOrNull {
            it.id == _movieId
        }
    }
}