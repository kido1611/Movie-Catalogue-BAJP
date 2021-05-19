package com.kido1611.dicoding.moviecatalogue.fragment.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.kido1611.dicoding.moviecatalogue.data.source.MovieRepository
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.kido1611.dicoding.moviecatalogue.utils.DataDummy
import com.kido1611.dicoding.moviecatalogue.utils.PagedAdapterTest
import com.kido1611.dicoding.moviecatalogue.utils.TestCoroutineRule
import com.kido1611.dicoding.moviecatalogue.utils.runTest
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class MoviesViewModelTest {
    private lateinit var viewModel: MoviesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private lateinit var repository: MovieRepository

    @MockK
    private lateinit var observer: Observer<PagingData<MovieEntity>>

    private lateinit var captureSlot: CapturingSlot<PagingData<MovieEntity>>
    private lateinit var adapter: PagedAdapterTest<MovieEntity>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = mockk(relaxed = true)
        viewModel = MoviesViewModel(repository)
        adapter = PagedAdapterTest()
        captureSlot = slot()
    }

    @Test
    fun `movies list() should return data`() = runTest(testCoroutineRule) {
        val dummyResult = MutableLiveData<PagingData<MovieEntity>>()
        val dummyMovies = DataDummy.generateDummyMovieEntities(true)
        val dummyPagingData = PagingData.from(dummyMovies)
        dummyResult.value = dummyPagingData

        every {
            repository.getDiscoverMovieMediator()
        } returns dummyResult
        every {
            observer.onChanged(capture(captureSlot))
        } answers { nothing }

        viewModel.loadList()
        val result = viewModel.list()
        result.observeForever(observer)
        verify(exactly = 1) { repository.getDiscoverMovieMediator() }

        launch {
            adapter.submitData(captureSlot.captured)
        }
        Assert.assertEquals(adapter.itemCount, dummyMovies.size)

        confirmVerified(repository)
    }

    @Test
    fun `movies list() should return empty data`() = runTest(testCoroutineRule) {
        val dummyResult = MutableLiveData<PagingData<MovieEntity>>()
        val dummyMovies = emptyList<MovieEntity>()
        val dummyPagingData = PagingData.from(dummyMovies)
        dummyResult.value = dummyPagingData

        every {
            repository.getDiscoverMovieMediator()
        } returns dummyResult
        every {
            observer.onChanged(capture(captureSlot))
        } answers { nothing }

        viewModel.loadList()
        val result = viewModel.list()
        result.observeForever(observer)
        verify(exactly = 1) { repository.getDiscoverMovieMediator() }

        launch {
            adapter.submitData(captureSlot.captured)
        }
        Assert.assertEquals(adapter.itemCount, 0)

        confirmVerified(repository)
    }
}