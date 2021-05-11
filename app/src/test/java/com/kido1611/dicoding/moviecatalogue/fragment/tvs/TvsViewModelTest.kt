package com.kido1611.dicoding.moviecatalogue.fragment.tvs

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
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvsViewModelTest {
    private lateinit var viewModel: TvsViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var observer: Observer<DiscoverUiState>

    @Test
    fun testGetMovies() {
        val dummyTvs = DataDummy.generateDummyTv()
        val dummySuccess = DiscoverUiState.Success(dummyTvs)
        val dummyResult = MutableLiveData<DiscoverUiState>()
        dummyResult.value = dummySuccess

        repository = mock()
        runBlocking {
            whenever(repository.getDiscoverTv()).thenReturn(dummyResult)
        }
        viewModel = TvsViewModel(repository)

        val result: LiveData<DiscoverUiState> = viewModel.list
        TestCase.assertNotNull(result)
        verify(repository).getDiscoverTv()
        TestCase.assertTrue(result.value is DiscoverUiState.Success)

        val tvList = result.value as DiscoverUiState.Success
        val tvs = tvList.list
        TestCase.assertEquals(10, tvs.size)

        val tv = tvs[0]
        TestCase.assertEquals(false, tv.isMovie())

        viewModel.list.observeForever(observer)
        verify(observer).onChanged(dummySuccess)
    }

    @Test
    fun testGetEmptyTvs() {
        val dummySuccessEmpty = DiscoverUiState.Success(emptyList())
        val dummyResult = MutableLiveData<DiscoverUiState>()
        dummyResult.value = dummySuccessEmpty

        repository = mock()
        runBlocking {
            whenever(repository.getDiscoverTv()).thenReturn(dummyResult)
        }
        viewModel = TvsViewModel(repository)

        val result: LiveData<DiscoverUiState> = viewModel.list
        TestCase.assertNotNull(result)
        verify(repository).getDiscoverTv()
        TestCase.assertTrue(result.value is DiscoverUiState.Success)

        val tvList = result.value as DiscoverUiState.Success
        val tvs = tvList.list
        TestCase.assertEquals(0, tvs.size)

        viewModel.list.observeForever(observer)
        verify(observer).onChanged(dummySuccessEmpty)
    }
}