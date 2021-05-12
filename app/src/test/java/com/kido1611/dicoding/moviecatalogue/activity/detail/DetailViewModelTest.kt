package com.kido1611.dicoding.moviecatalogue.activity.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kido1611.dicoding.moviecatalogue.data.source.MovieRepository
import com.kido1611.dicoding.moviecatalogue.data.source.UIState
import com.kido1611.dicoding.moviecatalogue.model.Movie
import com.kido1611.dicoding.moviecatalogue.utils.DataDummy
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val dummyTv = DataDummy.generateDummyTv()[0]

    private val movieId = dummyMovie.id
    private val tvId = dummyTv.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var observer: Observer<UIState<Movie>>

    private lateinit var dummyMovieSuccess: UIState.Success<Movie>
    private lateinit var dummyTvSuccess: UIState.Success<Movie>
    private lateinit var dummyFailed: UIState.Error

    @Before
    fun setup() {
        val resultMovie = MutableLiveData<UIState<Movie>>()
        dummyMovieSuccess = UIState.Success(dummyMovie)
        resultMovie.value = dummyMovieSuccess

        val resultTv = MutableLiveData<UIState<Movie>>()
        dummyTvSuccess = UIState.Success(dummyTv)
        resultTv.value = dummyTvSuccess

        dummyFailed = UIState.Error("Error")

        repository = mock()
        runBlocking {
            whenever(repository.getMovieById(movieId)).thenReturn(resultMovie)
            whenever(repository.getTvById(tvId)).thenReturn(resultTv)
        }

        viewModel = DetailViewModel(repository)
    }

    @Test
    fun testGetMovie() {
        viewModel.setMovie(true, movieId)

        val result: LiveData<UIState<Movie>> = viewModel.getMovie()
        Assert.assertNotNull(result)
        verify(repository).getMovieById(movieId)
        assertTrue(result.value is UIState.Success)

        val movie = (result.value as UIState.Success).data
        assertEquals(true, movie.isMovie())
        assertEquals(dummyMovie.title, movie.title)
        assertEquals(dummyMovie.id, movie.id)

        viewModel.getMovie().observeForever(observer)
        verify(observer).onChanged(dummyMovieSuccess)
    }

    @Test
    fun testGetTv() {
        viewModel.setMovie(false, tvId)

        val result: LiveData<UIState<Movie>> = viewModel.getMovie()
        Assert.assertNotNull(result)
        verify(repository).getTvById(tvId)
        assertTrue(result.value is UIState.Success)

        val tv = (result.value as UIState.Success).data
        assertEquals(false, tv.isMovie())
        assertEquals(dummyTv.title, tv.title)
        assertEquals(dummyTv.id, tv.id)

        viewModel.getMovie().observeForever(observer)
        verify(observer).onChanged(dummyTvSuccess)
    }

    @Test
    fun testMovieNotFound() {
        viewModel.setMovie(true, 112346)
        val responseResult = MutableLiveData<UIState<Movie>>()
        responseResult.value = UIState.Error("Error")

        whenever(repository.getMovieById(112346)).thenReturn(responseResult)
        val result: LiveData<UIState<Movie>> = viewModel.getMovie()
        Assert.assertNotNull(result)
        verify(repository).getMovieById(112346)
        assertTrue(result.value is UIState.Error)

        viewModel.getMovie().observeForever(observer)
        verify(observer).onChanged(dummyFailed)
    }

    @Test
    fun testTvNotFound() {
        viewModel.setMovie(false, 112346)
        val responseResult = MutableLiveData<UIState<Movie>>()
        responseResult.value = UIState.Error("Error")

        whenever(repository.getTvById(112346)).thenReturn(responseResult)
        val result: LiveData<UIState<Movie>> = viewModel.getMovie()
        Assert.assertNotNull(result)
        verify(repository).getTvById(112346)
        assertTrue(result.value is UIState.Error)

        viewModel.getMovie().observeForever(observer)
        verify(observer).onChanged(dummyFailed)
    }
}