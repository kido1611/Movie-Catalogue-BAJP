package com.kido1611.dicoding.moviecatalogue.activity.detail

import com.kido1611.dicoding.moviecatalogue.model.Movie
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setup() {
        viewModel = DetailViewModel()
    }

    private fun getMovie(): Movie? {
        viewModel.setMovie(true, 332562)
        return viewModel.getMovie()
    }

    private fun getTv(): Movie? {
        viewModel.setMovie(false, 1412)
        return viewModel.getMovie()
    }

    @Test
    fun testGetMovie() {
        val movie = getMovie()
        Assert.assertNotNull(movie)
    }

    @Test
    fun testIsCorrectMovie() {
        val movie = getMovie()
        Assert.assertEquals("A Star Is Born", movie?.getMovieTitle())
    }

    @Test
    fun testMovieNotAvailable() {
        viewModel.setMovie(true, 123456)
        val movie = viewModel.getMovie()
        Assert.assertNull(movie)
    }

    @Test
    fun testGetTv() {
        val tv = getTv()
        Assert.assertNotNull(tv)
    }

    @Test
    fun testIsCorrectTv() {
        val tv = getTv()
        Assert.assertEquals("Arrow", tv?.getMovieTitle())
    }

    @Test
    fun testTvNotAvailable() {
        viewModel.setMovie(false, 123456)
        val tv = viewModel.getMovie()
        Assert.assertNull(tv)
    }
}