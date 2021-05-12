package com.kido1611.dicoding.moviecatalogue.activity.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kido1611.dicoding.moviecatalogue.data.source.MovieRepository
import com.kido1611.dicoding.moviecatalogue.data.source.UIState
import com.kido1611.dicoding.moviecatalogue.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private lateinit var movieQuery: MovieQuery

    fun setMovie(isMovie: Boolean, movieId: Int) {
        movieQuery = MovieQuery(
            isMovie = isMovie,
            id = movieId
        )
    }

    fun getMovie(): LiveData<UIState<Movie>> = if (movieQuery.isMovie) {
        repository.getMovieById(movieQuery.id)
    } else {
        repository.getTvById(movieQuery.id)
    }
}

data class MovieQuery(
    val isMovie: Boolean,
    val id: Int
)