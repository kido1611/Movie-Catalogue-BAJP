package com.kido1611.dicoding.moviecatalogue.fragment.tvs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kido1611.dicoding.moviecatalogue.data.source.DiscoverUiState
import com.kido1611.dicoding.moviecatalogue.data.source.MovieRepository
import com.kido1611.dicoding.moviecatalogue.fragment.movies.MoviesViewModel
import com.kido1611.dicoding.moviecatalogue.utils.DataDummy
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvsViewModelTest {
    private lateinit var viewModel: MoviesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    private lateinit var dummySuccess: DiscoverUiState

    @Before
    fun setup() {
        val dummyTvs = DataDummy.generateDummyTv()
        dummySuccess = DiscoverUiState.Success(dummyTvs)
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
        TestCase.assertNotNull(result)
        verify(repository).getDiscoverMovie()
        TestCase.assertTrue(result.value is DiscoverUiState.Success)

        val tvList = result.value as DiscoverUiState.Success
        val tvs = tvList.list
        TestCase.assertEquals(10, tvs.size)

        viewModel.list.observeForever(observer)
        verify(observer).onChanged(dummySuccess)
    }

    @Test
    fun testIsRealMovieOnGetMovies() {
        val result: LiveData<DiscoverUiState> = viewModel.list
        TestCase.assertNotNull(result)
        verify(repository).getDiscoverMovie()
        TestCase.assertTrue(result.value is DiscoverUiState.Success)

        val tvList = result.value as DiscoverUiState.Success
        val tvs = tvList.list
        val tv = tvs[0]
        TestCase.assertEquals(false, tv.isMovie())
    }
}