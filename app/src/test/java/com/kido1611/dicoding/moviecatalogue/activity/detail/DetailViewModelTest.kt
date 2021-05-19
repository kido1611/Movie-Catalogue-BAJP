package com.kido1611.dicoding.moviecatalogue.activity.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kido1611.dicoding.moviecatalogue.data.source.MovieRepository
import com.kido1611.dicoding.moviecatalogue.data.source.UIState
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieBookmark
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.kido1611.dicoding.moviecatalogue.utils.DataDummy
import com.kido1611.dicoding.moviecatalogue.utils.TestCoroutineRule
import com.kido1611.dicoding.moviecatalogue.utils.runTest
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

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private lateinit var repository: MovieRepository

    @MockK
    private lateinit var observerMovieEntity: Observer<UIState<MovieEntity>>

    @MockK
    private lateinit var observerMovieBookmark: Observer<MovieBookmark>

    private val observerNullMovieBookmark = mockk<Observer<MovieBookmark>> {
        every {
            onChanged(any())
        } just Runs
    }

    private lateinit var csUiStateMovieEntity: CapturingSlot<UIState<MovieEntity>>
    private lateinit var csMovieBookmark: CapturingSlot<MovieBookmark>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = mockk(relaxed = true)
        viewModel = DetailViewModel(repository)
        csUiStateMovieEntity = slot()
        csMovieBookmark = slot()
    }

    @Test
    fun `getMovie() should return movie data`() = runTest(testCoroutineRule) {
        val dummyResult = MutableLiveData<UIState<MovieEntity>>()
        val dummyMovie = DataDummy.generateDummyMovieEntity(0, true)
        dummyResult.value = UIState.Success(dummyMovie)

        val selectedMovieId = dummyMovie.movie_id

        every {
            repository.getMovieById(selectedMovieId)
        } returns dummyResult
        every {
            observerMovieEntity.onChanged(capture(csUiStateMovieEntity))
        } answers { nothing }

        viewModel.setMovie(true, selectedMovieId)
        val resultLiveData = viewModel.getMovie()
        resultLiveData.observeForever(observerMovieEntity)
        verify(exactly = 1) { repository.getMovieById(selectedMovieId) }

        Assert.assertTrue(csUiStateMovieEntity.captured is UIState.Success)
        val result = csUiStateMovieEntity.captured as UIState.Success
        val movie = result.data
        Assert.assertTrue(movie.isMovie())
    }

    @Test
    fun `getMovie() should return tv show data`() = runTest(testCoroutineRule) {
        val dummyResult = MutableLiveData<UIState<MovieEntity>>()
        val dummyMovie = DataDummy.generateDummyMovieEntity(0, false)
        dummyResult.value = UIState.Success(dummyMovie)

        val selectedMovieId = dummyMovie.movie_id

        every {
            repository.getTvById(selectedMovieId)
        } returns dummyResult
        every {
            observerMovieEntity.onChanged(capture(csUiStateMovieEntity))
        } answers { nothing }

        viewModel.setMovie(false, selectedMovieId)
        val resultLiveData = viewModel.getMovie()
        resultLiveData.observeForever(observerMovieEntity)
        verify(exactly = 1) { repository.getTvById(selectedMovieId) }

        Assert.assertTrue(csUiStateMovieEntity.captured is UIState.Success)
        val result = csUiStateMovieEntity.captured as UIState.Success
        val movie = result.data
        Assert.assertFalse(movie.isMovie())
    }

    @Test
    fun `getMovie() should return error movie result`() = runTest(testCoroutineRule) {
        val dummyResult = MutableLiveData<UIState<MovieEntity>>()
        dummyResult.value = UIState.Error("Error")

        val selectedMovieId = 1

        every {
            repository.getMovieById(selectedMovieId)
        } returns dummyResult
        every {
            observerMovieEntity.onChanged(capture(csUiStateMovieEntity))
        } answers { nothing }

        viewModel.setMovie(true, selectedMovieId)
        val resultLiveData = viewModel.getMovie()
        resultLiveData.observeForever(observerMovieEntity)
        verify(exactly = 1) { repository.getMovieById(selectedMovieId) }

        Assert.assertTrue(csUiStateMovieEntity.captured is UIState.Error)
        val result = csUiStateMovieEntity.captured as UIState.Error
        val message = result.message
        Assert.assertEquals(message, "Error")
    }

    @Test
    fun `getMovie() should return error tv result`() = runTest(testCoroutineRule) {
        val dummyResult = MutableLiveData<UIState<MovieEntity>>()
        dummyResult.value = UIState.Error("Error")

        val selectedMovieId = 1

        every {
            repository.getTvById(selectedMovieId)
        } returns dummyResult
        every {
            observerMovieEntity.onChanged(capture(csUiStateMovieEntity))
        } answers { nothing }

        viewModel.setMovie(false, selectedMovieId)
        val resultLiveData = viewModel.getMovie()
        resultLiveData.observeForever(observerMovieEntity)
        verify(exactly = 1) { repository.getTvById(selectedMovieId) }

        Assert.assertTrue(csUiStateMovieEntity.captured is UIState.Error)
        val result = csUiStateMovieEntity.captured as UIState.Error
        val message = result.message
        Assert.assertEquals(message, "Error")
    }

    @Test
    fun `toggleBookmark() should call addBookmarkMovie() when movie not bookmarked`() =
        runTest(testCoroutineRule) {
            val dummyResult = MutableLiveData<MovieBookmark>()
            val dummyMovieBookmark = DataDummy.generateDummyMovieBookmark(0, true)
            dummyResult.value = dummyMovieBookmark
            every {
                repository.addBookmarkMovie(dummyMovieBookmark)
            } answers { nothing }

            viewModel.setIsBookmarked(false)
            viewModel.toggleBookmark(dummyMovieBookmark)
            verify(exactly = 1) { repository.addBookmarkMovie(dummyMovieBookmark) }
        }

    @Test
    fun `toggleBookmark() should call deleteBookmarkMovie() when movie bookmarked`() =
        runTest(testCoroutineRule) {
            val dummyResult = MutableLiveData<MovieBookmark>()
            val dummyMovieBookmark = DataDummy.generateDummyMovieBookmark(0, true)
            dummyResult.value = dummyMovieBookmark

            val movieId = dummyMovieBookmark.movie_id

            every {
                repository.deleteBookmarkMovie(movieId)
            } answers { nothing }

            viewModel.setIsBookmarked(true)
            viewModel.toggleBookmark(dummyMovieBookmark)
            verify(exactly = 1) { repository.deleteBookmarkMovie(movieId) }
        }

    @Test
    fun `getBookmarkMovie() should return data when movie bookmarked`() =
        runTest(testCoroutineRule) {
            val dummyResult = MutableLiveData<MovieBookmark>()
            val dummyMovieBookmark = DataDummy.generateDummyMovieBookmark(0, true)
            dummyResult.value = dummyMovieBookmark

            val selectedMovieId = dummyMovieBookmark.movie_id
            every {
                repository.getBookmarkMovie(selectedMovieId)
            } returns dummyResult
            every {
                observerMovieBookmark.onChanged(capture(csMovieBookmark))
            } answers { nothing }

            viewModel.setMovie(false, selectedMovieId)
            val result = viewModel.getBookmarkMovie()
            result.observeForever(observerMovieBookmark)
            verify(exactly = 1) { repository.getBookmarkMovie(selectedMovieId) }

            Assert.assertNotNull(csMovieBookmark.captured)
        }

    @Test
    fun `getBookmarkMovie() should return null when movie not bookmarked`() =
        runTest(testCoroutineRule) {
            val dummyResult = MutableLiveData<MovieBookmark>()
            dummyResult.value = null

            val dummyMovieBookmark = DataDummy.generateDummyMovieBookmark(0, true)
            val selectedMovieId = dummyMovieBookmark.movie_id

            every {
                repository.getBookmarkMovie(selectedMovieId)
            } returns dummyResult

            viewModel.setMovie(false, selectedMovieId)
            val result = viewModel.getBookmarkMovie()
            result.observeForever(observerNullMovieBookmark)
            verify(exactly = 1) { repository.getBookmarkMovie(selectedMovieId) }

            verifySequence {
                observerNullMovieBookmark.onChanged(null)
            }
        }
}