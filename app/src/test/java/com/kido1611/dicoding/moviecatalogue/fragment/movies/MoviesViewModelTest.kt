package com.kido1611.dicoding.moviecatalogue.fragment.movies

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MoviesViewModelTest {
    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setup() {
        viewModel = MoviesViewModel()
    }

    @Test
    fun testGetMovies() {
        val movies = viewModel.list()
        Assert.assertEquals(10, movies.size)
    }

    @Test
    fun testIsRealMovieOnGetMovies() {
        val movie = viewModel.list()[0]
        Assert.assertEquals(true, movie.isMovie())
    }

}