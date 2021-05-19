package com.kido1611.dicoding.moviecatalogue.fragment.tvs

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
class TvsViewModelTest {
    private lateinit var viewModel: TvsViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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
        viewModel = TvsViewModel(repository)
        adapter = PagedAdapterTest()
        captureSlot = slot()
    }

    @Test
    fun `tvs list() should result data`() = runTest(testCoroutineRule) {
        val dummyResult = MutableLiveData<PagingData<MovieEntity>>()
        val dummyTvs = DataDummy.generateDummyMovieEntities(false)
        val dummyPagingData = PagingData.from(dummyTvs)
        dummyResult.value = dummyPagingData

        every {
            repository.getDiscoverTvMediator()
        } returns dummyResult
        every {
            observer.onChanged(capture(captureSlot))
        } answers { nothing }

        viewModel.loadList()
        val result = viewModel.list()
        result.observeForever(observer)
        verify(exactly = 1) { repository.getDiscoverTvMediator() }

        launch {
            adapter.submitData(captureSlot.captured)
        }
        Assert.assertEquals(adapter.itemCount, dummyTvs.size)

        confirmVerified(repository)
    }

    @Test
    fun `tvs list() should return empty data`() = runTest(testCoroutineRule) {
        val dummyResult = MutableLiveData<PagingData<MovieEntity>>()
        val dummyTvs = emptyList<MovieEntity>()
        val dummyPagingData = PagingData.from(dummyTvs)
        dummyResult.value = dummyPagingData

        every {
            repository.getDiscoverTvMediator()
        } returns dummyResult
        every {
            observer.onChanged(capture(captureSlot))
        } answers { nothing }

        viewModel.loadList()
        val result = viewModel.list()
        result.observeForever(observer)
        verify(exactly = 1) { repository.getDiscoverTvMediator() }

        launch {
            adapter.submitData(captureSlot.captured)
        }
        Assert.assertEquals(adapter.itemCount, 0)

        confirmVerified(repository)
    }
}