package com.kido1611.dicoding.moviecatalogue.fragment.bookmark_tvs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.kido1611.dicoding.moviecatalogue.data.source.MovieRepository
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieBookmark
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

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class BookmarkTvsViewModelTest {
    private lateinit var viewModel: BookmarkTvsViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private lateinit var repository: MovieRepository

    @MockK
    private lateinit var observer: Observer<PagingData<MovieBookmark>>

    private lateinit var captureSlot: CapturingSlot<PagingData<MovieBookmark>>
    private lateinit var adapter: PagedAdapterTest<MovieBookmark>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = mockk(relaxed = true)
        viewModel = BookmarkTvsViewModel(repository)
        adapter = PagedAdapterTest()
        captureSlot = slot()
    }

    @Test
    fun `bookmarked movies list() should result data`() = runTest(testCoroutineRule) {
        val dummyResult = MutableLiveData<PagingData<MovieBookmark>>()
        val dummyMovies = DataDummy.generateDummyMovieBookmarks(false)
        val dummyPagingData = PagingData.from(dummyMovies)
        dummyResult.value = dummyPagingData

        every {
            repository.getBookmarkedTvs()
        } returns dummyResult
        every {
            observer.onChanged(capture(captureSlot))
        } answers { nothing }

        viewModel.loadList()
        val result = viewModel.list()
        result.observeForever(observer)
        verify(exactly = 1) {
            repository.getBookmarkedTvs()
        }

        launch {
            adapter.submitData(captureSlot.captured)
        }
        Assert.assertEquals(adapter.itemCount, dummyMovies.size)

        confirmVerified(repository)
    }

    @Test
    fun `bookmarked movies list() should result empty data`() = runTest(testCoroutineRule) {
        val dummyResult = MutableLiveData<PagingData<MovieBookmark>>()
        val dummyMovies = emptyList<MovieBookmark>()
        val dummyPagingData = PagingData.from(dummyMovies)
        dummyResult.value = dummyPagingData

        every {
            repository.getBookmarkedTvs()
        } returns dummyResult
        every {
            observer.onChanged(capture(captureSlot))
        } answers { nothing }

        viewModel.loadList()
        val result = viewModel.list()
        result.observeForever(observer)
        verify(exactly = 1) {
            repository.getBookmarkedTvs()
        }

        launch {
            adapter.submitData(captureSlot.captured)
        }
        Assert.assertEquals(adapter.itemCount, dummyMovies.size)

        confirmVerified(repository)
    }
}