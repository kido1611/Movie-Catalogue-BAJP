package com.kido1611.dicoding.moviecatalogue.fragment.movies

import androidx.lifecycle.ViewModel
import com.kido1611.dicoding.moviecatalogue.model.Movie
import com.kido1611.dicoding.moviecatalogue.utils.DataDummy

class MoviesViewModel : ViewModel() {
    fun list(): List<Movie> = DataDummy.generateDummyMovies()
}