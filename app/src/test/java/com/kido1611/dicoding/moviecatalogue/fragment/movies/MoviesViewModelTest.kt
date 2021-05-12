package com.kido1611.dicoding.moviecatalogue.fragment.movies

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
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {
    private lateinit var viewModel: MoviesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var observer: Observer<UIState<List<Movie>>>

    @Test
    fun testGetMovies() {
        val dummyMovies = DataDummy.generateDummyMovies()
        val dummySuccess = UIState.Success(dummyMovies)
        val dummyResult = MutableLiveData<UIState<List<Movie>>>()
        dummyResult.value = dummySuccess

        repository = mock()
        runBlocking {
            whenever(repository.getDiscoverMovie()).thenReturn(dummyResult)
        }
        viewModel = MoviesViewModel(repository)

        val result: LiveData<UIState<List<Movie>>> = viewModel.list
        assertNotNull(result)
        verify(repository).getDiscoverMovie()
        assertTrue(result.value is UIState.Success)

        val movieList = result.value as UIState.Success
        val movies = movieList.data
        assertEquals(10, movies.size)

        val movie = movies[0]
        assertEquals(true, movie.isMovie())

        viewModel.list.observeForever(observer)
        verify(observer).onChanged(dummySuccess)
    }

    @Test
    fun testGetEmptyMovies() {
        val dummySuccessEmpty = UIState.Success(emptyList<Movie>())
        val dummyResult = MutableLiveData<UIState<List<Movie>>>()
        dummyResult.value = dummySuccessEmpty

        repository = mock()
        runBlocking {
            whenever(repository.getDiscoverMovie()).thenReturn(dummyResult)
        }
        viewModel = MoviesViewModel(repository)

        val result: LiveData<UIState<List<Movie>>> = viewModel.list
        assertNotNull(result)
        verify(repository).getDiscoverMovie()
        assertTrue(result.value is UIState.Success)

        val movieList = result.value as UIState.Success
        val movies = movieList.data
        assertEquals(0, movies.size)

        viewModel.list.observeForever(observer)
        verify(observer).onChanged(dummySuccessEmpty)
    }
}