package com.kido1611.dicoding.moviecatalogue.fragment.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kido1611.dicoding.moviecatalogue.data.source.DiscoverUiState
import com.kido1611.dicoding.moviecatalogue.data.source.MovieRepository
import com.kido1611.dicoding.moviecatalogue.utils.DataDummy
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.*

import kotlinx.coroutines.runBlocking
import org.junit.Before
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

    private lateinit var dummySuccess: DiscoverUiState

    @Before
    fun setup() {
        val dummyMovies = DataDummy.generateDummyMovies()
        dummySuccess = DiscoverUiState.Success(dummyMovies)
        val dummyResult = MutableLiveData<DiscoverUiState>()
        dummyResult.value = dummySuccess

        repository = mock()
        runBlocking {
            whenever(repository.getDiscoverMovie()).thenReturn(dummyResult)

        }
        viewModel = MoviesViewModel(repository)
    }

    @Mock
    private lateinit var observer: Observer<DiscoverUiState>

    @Test
    fun testGetMovies() {
        val result: LiveData<DiscoverUiState> = viewModel.list
        assertNotNull(result)
        verify(repository).getDiscoverMovie()
        assertTrue(result.value is DiscoverUiState.Success)

        val movieList = result.value as DiscoverUiState.Success
        val movies = movieList.list
        assertEquals(10, movies.size)

        viewModel.list.observeForever(observer)
        verify(observer).onChanged(dummySuccess)
    }

    @Test
    fun testIsRealMovieOnGetMovies() {
        val result: LiveData<DiscoverUiState> = viewModel.list
        assertNotNull(result)
        verify(repository).getDiscoverMovie()
        assertTrue(result.value is DiscoverUiState.Success)

        val movieList = result.value as DiscoverUiState.Success
        val movies = movieList.list
        val movie = movies[0]
        assertEquals(true, movie.isMovie())
    }
}