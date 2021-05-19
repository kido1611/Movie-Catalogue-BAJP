package com.kido1611.dicoding.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.kido1611.dicoding.moviecatalogue.data.source.local.LocalDataSource
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieBookmark
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.kido1611.dicoding.moviecatalogue.data.source.paging.PagingDataSource
import com.kido1611.dicoding.moviecatalogue.data.source.remote.ApiResponse
import com.kido1611.dicoding.moviecatalogue.data.source.remote.RemoteDataSource
import com.kido1611.dicoding.moviecatalogue.data.source.remote.entity.MovieResponse
import com.kido1611.dicoding.moviecatalogue.utils.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private lateinit var remote: RemoteDataSource

    @MockK
    private lateinit var local: LocalDataSource

    @MockK
    private lateinit var paging: PagingDataSource

    @MockK
    private lateinit var appExecutors: AppExecutors

    private lateinit var repository: FakeMovieRepository

    private val observerGetMovieById = mockk<Observer<UIState<MovieEntity>>> {
        every {
            onChanged(any())
        } just Runs
    }

    private val observerDiscoverMovies = mockk<Observer<PagingData<MovieEntity>>> {
        every {
            onChanged(any())
        } just Runs
    }

    private val observerGetBookmarkMovie = mockk<Observer<MovieBookmark>> {
        every {
            onChanged(any())
        } just Runs
    }

    private lateinit var adapterMovieEntity: PagedAdapterTest<MovieEntity>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        remote = mockk(relaxed = true)
        local = mockk(relaxed = true)
        paging = mockk(relaxed = true)
        appExecutors = AppExecutors()

        repository = FakeMovieRepository(
            appExecutors, local, remote, paging
        )

        adapterMovieEntity = PagedAdapterTest()
    }

    @Test
    fun `test getMovieById() should return data`() = runTest(testCoroutineRule) {
        val dummyMovie = DataDummy.generateDummyMovieEntity(0, true)
        val dummyResult = MutableLiveData<MovieEntity>()
        dummyResult.value = dummyMovie

        val selectedId = dummyMovie.movie_id

        every {
            local.getMovieById(selectedId)
        } returns dummyResult

        val resultLiveData = repository.getMovieById(selectedId)
        resultLiveData.observeForever(observerGetMovieById)
        verify(exactly = 1) {
            local.getMovieById(selectedId)
        }

        verifySequence {
            observerGetMovieById.onChanged(UIState.Loading(dummyMovie))
        }
        val result = resultLiveData.value
        Assert.assertTrue(result is UIState.Loading)

        val movie = (result as UIState.Loading).data
        Assert.assertNotNull(movie)
        Assert.assertEquals(movie?.movie_id, dummyMovie.movie_id)
    }

    @Test
    fun `test getMovieById() should call network`() = runTest(testCoroutineRule) {
        val dummyMovieResponse = DataDummy.generateDummyMovieResponse(0, true)
        val dummyRemoteResponse = ApiResponse.Success(dummyMovieResponse)
        val dummyRemoteResult = MutableLiveData<ApiResponse<MovieResponse>>()
        dummyRemoteResult.value = dummyRemoteResponse

        val dummyMovieEntity = dummyMovieResponse.toMovieEntity()
        val dummyLocalResult = MutableLiveData<MovieEntity>()
        dummyLocalResult.value = dummyMovieEntity

        val selectedId = dummyMovieResponse.id

        every {
            local.getMovieById(selectedId)
        } returns dummyLocalResult
        every {
            remote.getMovieById(selectedId)
        } returns dummyRemoteResult

        val resultLiveData = repository.getMovieById(selectedId)
        resultLiveData.observeForever(observerGetMovieById)
        verify(exactly = 1) {
            remote.getMovieById(selectedId)
        }
    }

    @Test
    fun `test getTvById() should return data`() = runTest(testCoroutineRule) {
        val dummyMovie = DataDummy.generateDummyMovieEntity(0, false)
        val dummyResult = MutableLiveData<MovieEntity>()
        dummyResult.value = dummyMovie

        val selectedId = dummyMovie.movie_id

        every {
            local.getMovieById(selectedId)
        } returns dummyResult

        val resultLiveData = repository.getMovieById(selectedId)
        resultLiveData.observeForever(observerGetMovieById)
        verify(exactly = 1) {
            local.getMovieById(selectedId)
        }

        verifySequence {
            observerGetMovieById.onChanged(UIState.Loading(dummyMovie))
        }
        val result = resultLiveData.value
        Assert.assertTrue(result is UIState.Loading)

        val movie = (result as UIState.Loading).data
        Assert.assertNotNull(movie)
        Assert.assertEquals(movie?.movie_id, dummyMovie.movie_id)
    }

    @Test
    fun `test getTvById() should call network`() = runTest(testCoroutineRule) {
        val dummyMovieResponse = DataDummy.generateDummyMovieResponse(0, false)
        val dummyRemoteResponse = ApiResponse.Success(dummyMovieResponse)
        val dummyRemoteResult = MutableLiveData<ApiResponse<MovieResponse>>()
        dummyRemoteResult.value = dummyRemoteResponse

        val dummyMovieEntity = dummyMovieResponse.toMovieEntity()
        val dummyLocalResult = MutableLiveData<MovieEntity>()
        dummyLocalResult.value = dummyMovieEntity

        val selectedId = dummyMovieResponse.id

        every {
            local.getMovieById(selectedId)
        } returns dummyLocalResult
        every {
            remote.getTvById(selectedId)
        } returns dummyRemoteResult

        val resultLiveData = repository.getTvById(selectedId)
        resultLiveData.observeForever(observerGetMovieById)
        verify(exactly = 1) {
            remote.getTvById(selectedId)
        }
    }

    @Test
    fun `test getDiscoverMovieMediator() should return data`() = runTest(testCoroutineRule) {
        val dummyMovies = DataDummy.generateDummyMovieEntities(true)
        val dummyPagingData = PagingData.from(dummyMovies)
        val dummyResult = MutableLiveData<PagingData<MovieEntity>>()
        dummyResult.value = dummyPagingData

        every {
            paging.getDiscoverMovies()
        } returns dummyResult

        val resultLiveData = repository.getDiscoverMovieMediator()
        resultLiveData.observeForever(observerDiscoverMovies)
        verify(exactly = 1) {
            paging.getDiscoverMovies()
        }

        verifySequence {
            observerDiscoverMovies.onChanged(dummyPagingData)
        }

        val result = resultLiveData.value
        Assert.assertNotNull(result)

        result?.let {
            adapterMovieEntity.submitData(it)
        }
        Assert.assertEquals(adapterMovieEntity.itemCount, dummyMovies.size)
    }

    @Test
    fun `test getDiscoverMovieMediator() should return empty data`() = runTest(testCoroutineRule) {
        val dummyPagingData = PagingData.from(emptyList<MovieEntity>())
        val dummyResult = MutableLiveData<PagingData<MovieEntity>>()
        dummyResult.value = dummyPagingData

        every {
            paging.getDiscoverMovies()
        } returns dummyResult

        val resultLiveData = repository.getDiscoverMovieMediator()
        resultLiveData.observeForever(observerDiscoverMovies)
        verify(exactly = 1) {
            paging.getDiscoverMovies()
        }

        verifySequence {
            observerDiscoverMovies.onChanged(dummyPagingData)
        }

        val result = resultLiveData.value
        Assert.assertNotNull(result)

        result?.let {
            adapterMovieEntity.submitData(it)
        }
        Assert.assertEquals(adapterMovieEntity.itemCount, 0)
    }

    @Test
    fun `test getDiscoverTvMediator() should return data`() = runTest(testCoroutineRule) {
        val dummyMovies = DataDummy.generateDummyMovieEntities(false)
        val dummyPagingData = PagingData.from(dummyMovies)
        val dummyResult = MutableLiveData<PagingData<MovieEntity>>()
        dummyResult.value = dummyPagingData

        every {
            paging.getDiscoverTvs()
        } returns dummyResult

        val resultLiveData = repository.getDiscoverTvMediator()
        resultLiveData.observeForever(observerDiscoverMovies)
        verify(exactly = 1) {
            paging.getDiscoverTvs()
        }

        verifySequence {
            observerDiscoverMovies.onChanged(dummyPagingData)
        }

        val result = resultLiveData.value
        Assert.assertNotNull(result)

        result?.let {
            adapterMovieEntity.submitData(it)
        }
        Assert.assertEquals(adapterMovieEntity.itemCount, dummyMovies.size)
    }

    @Test
    fun `test getDiscoverTvMediator() should return empty data`() = runTest(testCoroutineRule) {
        val dummyPagingData = PagingData.from(emptyList<MovieEntity>())
        val dummyResult = MutableLiveData<PagingData<MovieEntity>>()
        dummyResult.value = dummyPagingData

        every {
            paging.getDiscoverTvs()
        } returns dummyResult

        val resultLiveData = repository.getDiscoverTvMediator()
        resultLiveData.observeForever(observerDiscoverMovies)
        verify(exactly = 1) {
            paging.getDiscoverTvs()
        }

        verifySequence {
            observerDiscoverMovies.onChanged(dummyPagingData)
        }

        val result = resultLiveData.value
        Assert.assertNotNull(result)

        result?.let {
            adapterMovieEntity.submitData(it)
        }
        Assert.assertEquals(adapterMovieEntity.itemCount, 0)
    }

    @Test
    fun `test addBookmarkMovie() should call local function`() = runTest(testCoroutineRule) {
        val dummyMovieBookmark = DataDummy.generateDummyMovieBookmark(0, true)

        every {
            local.addBookmarkMovie(dummyMovieBookmark)
        } answers { nothing }
        repository.addBookmarkMovie(dummyMovieBookmark)
        verify(exactly = 1) {
            local.addBookmarkMovie(dummyMovieBookmark)
        }
    }

    @Test
    fun `test deleteBookmarkMovie() should call local function`() = runTest(testCoroutineRule) {
        every {
            local.removeBookmarkMovie(1)
        } answers { nothing }
        repository.deleteBookmarkMovie(1)
        verify(exactly = 1) {
            local.removeBookmarkMovie(1)
        }
    }

    @Test
    fun `test getBookmarkMovie() should return data`() = runTest(testCoroutineRule) {
        val dummyMovieBookmark = DataDummy.generateDummyMovieBookmark(0, true)
        val dummyResult = MutableLiveData<MovieBookmark>()
        dummyResult.value = dummyMovieBookmark

        val selectedMovieId = dummyMovieBookmark.movie_id

        every {
            local.getMovieBookmarked(selectedMovieId)
        } returns dummyResult

        val result = repository.getBookmarkMovie(selectedMovieId)
        result.observeForever(observerGetBookmarkMovie)
        verify(exactly = 1) {
            local.getMovieBookmarked(selectedMovieId)
        }
        verifySequence {
            observerGetBookmarkMovie.onChanged(dummyMovieBookmark)
        }
        val movieBookmark = result.value
        Assert.assertNotNull(movieBookmark)
    }

    @Test
    fun `test getBookmarkMovie() should return null data`() = runTest(testCoroutineRule) {
        val dummyResult = MutableLiveData<MovieBookmark>(null)

        val selectedMovieId = 1
        every {
            local.getMovieBookmarked(selectedMovieId)
        } returns dummyResult

        val result = repository.getBookmarkMovie(selectedMovieId)
        result.observeForever(observerGetBookmarkMovie)
        verify(exactly = 1) {
            local.getMovieBookmarked(selectedMovieId)
        }
        verifySequence {
            observerGetBookmarkMovie.onChanged(null)
        }
        val movieBookmark = result.value
        Assert.assertNull(movieBookmark)
    }
}
