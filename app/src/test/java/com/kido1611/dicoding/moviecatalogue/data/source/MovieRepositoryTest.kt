package com.kido1611.dicoding.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.kido1611.dicoding.moviecatalogue.data.source.remote.RemoteDataSource
import com.kido1611.dicoding.moviecatalogue.model.Movie
import com.kido1611.dicoding.moviecatalogue.utils.DataDummy
import com.kido1611.dicoding.moviecatalogue.utils.LiveDataTestUtil
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val movieRepository = FakeMovieRepository(remote)

    private val dummyMovies = DataDummy.generateDummyMovies()
    private val dummyTvs = DataDummy.generateDummyTv()
    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val dummyTv = DataDummy.generateDummyTv()[0]

    @Test
    fun testGetDiscoverMovie() {
        val result = MutableLiveData<UIState<List<Movie>>>()
        result.value = UIState.Success(dummyMovies)

        `when`(remote.getDiscoverMovie())
            .thenReturn(result)

        val responseLiveData = LiveDataTestUtil.getValue(movieRepository.getDiscoverMovie())
        assertNotNull(responseLiveData)
        assertTrue(responseLiveData is UIState.Success<List<Movie>>)

        val movies = (responseLiveData as UIState.Success<List<Movie>>).data
        assertEquals(movies, dummyMovies)
        assertEquals(movies.size, 10)
    }

    @Test
    fun testGetDiscoverTv() {
        val result = MutableLiveData<UIState<List<Movie>>>()
        result.value = UIState.Success(dummyTvs)

        `when`(remote.getDiscoverTv())
            .thenReturn(result)

        val responseLiveData = LiveDataTestUtil.getValue(movieRepository.getDiscoverTv())
        assertNotNull(responseLiveData)
        assertTrue(responseLiveData is UIState.Success<List<Movie>>)

        val tvs = (responseLiveData as UIState.Success<List<Movie>>).data
        assertEquals(tvs, dummyTvs)
        assertEquals(tvs.size, 10)
    }

    @Test
    fun testGetMovieById() {
        val result = MutableLiveData<UIState<Movie>>()
        result.value = UIState.Success(dummyMovie)

        `when`(remote.getMovieById(dummyMovie.id))
            .thenReturn(result)

        val responseLiveData =
            LiveDataTestUtil.getValue(movieRepository.getMovieById(dummyMovie.id))
        assertNotNull(responseLiveData)
        assertTrue(responseLiveData is UIState.Success<Movie>)

        val movieLiveData = (responseLiveData as UIState.Success<Movie>)
        assertNotNull(movieLiveData)

        val movie = movieLiveData.data
        assertNotNull(movie)
        assertEquals(movie.id, dummyMovie.id)
        assertEquals(movie.getMovieTitle(), dummyMovie.getMovieTitle())
    }

    @Test
    fun testGetTvById() {
        val result = MutableLiveData<UIState<Movie>>()
        result.value = UIState.Success(dummyTv)

        `when`(remote.getTvById(dummyTv.id))
            .thenReturn(result)

        val responseLiveData = LiveDataTestUtil.getValue(movieRepository.getTvById(dummyTv.id))
        assertNotNull(responseLiveData)
        assertTrue(responseLiveData is UIState.Success<Movie>)

        val tvLiveData = (responseLiveData as UIState.Success<Movie>)
        assertNotNull(tvLiveData)

        val tv = tvLiveData.data
        assertNotNull(tv)
        assertEquals(tv.id, dummyTv.id)
        assertEquals(tv.getMovieTitle(), dummyTv.getMovieTitle())
    }
}