package com.kido1611.dicoding.moviecatalogue.activity.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kido1611.dicoding.moviecatalogue.data.source.MovieRepository
import com.kido1611.dicoding.moviecatalogue.data.source.UIState
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieBookmark
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieQuery = MutableLiveData<MovieQuery>()
    private val _isBookmarked = MutableLiveData(false)

    fun setMovie(isMovie: Boolean, movieId: Int) {
        _movieQuery.value = MovieQuery(
            isMovie = isMovie,
            id = movieId
        )
    }

    fun setIsBookmarked(isBookmark: Boolean) {
        _isBookmarked.value = isBookmark
    }

    fun getMovie(): LiveData<UIState<MovieEntity>> = Transformations.switchMap(_movieQuery) {
        if (it.isMovie) {
            repository.getMovieById(it.id)
        } else {
            repository.getTvById(it.id)
        }
    }

    fun toggleFavorite(movie: MovieBookmark) {
        if (_isBookmarked.value == true) {
            repository.deleteBookmarkMovie(movie.movie_id)
        } else {
            repository.addBookmarkMovie(movie)
        }
    }

    fun getBookmarkMovie(): LiveData<MovieBookmark> = Transformations.switchMap(_movieQuery) {
        repository.getBookmarkMovie(it.id)
    }
}

data class MovieQuery(
    val isMovie: Boolean,
    val id: Int
)