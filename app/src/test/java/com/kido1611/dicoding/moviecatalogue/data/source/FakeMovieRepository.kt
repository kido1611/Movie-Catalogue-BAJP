package com.kido1611.dicoding.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.kido1611.dicoding.moviecatalogue.data.source.local.LocalDataSource
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieBookmark
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.kido1611.dicoding.moviecatalogue.data.source.paging.PagingDataSource
import com.kido1611.dicoding.moviecatalogue.data.source.remote.ApiResponse
import com.kido1611.dicoding.moviecatalogue.data.source.remote.RemoteDataSource
import com.kido1611.dicoding.moviecatalogue.data.source.remote.entity.MovieResponse
import com.kido1611.dicoding.moviecatalogue.utils.AppExecutors

class FakeMovieRepository constructor(
    private val appExecutors: AppExecutors,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val pagingDataSource: PagingDataSource
) : MovieDataSource {
    override fun getMovieById(id: Int): LiveData<UIState<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> = localDataSource.getMovieById(id)

            override fun shouldFetch(data: MovieEntity?): Boolean = true

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> =
                remoteDataSource.getMovieById(id)

            override fun saveCallResult(data: MovieResponse) {
                val entity = data.toMovieEntity()
                localDataSource.updateMovieGenre(id, entity.genres)
            }
        }
            .asLiveData()
    }

    override fun getTvById(id: Int): LiveData<UIState<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> = localDataSource.getMovieById(id)

            override fun shouldFetch(data: MovieEntity?): Boolean = true

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> =
                remoteDataSource.getTvById(id)

            override fun saveCallResult(data: MovieResponse) {
                val entity = data.toMovieEntity()
                localDataSource.updateMovieGenre(id, entity.genres)
            }
        }
            .asLiveData()
    }

    @ExperimentalPagingApi
    override fun getDiscoverMovieMediator(): LiveData<PagingData<MovieEntity>> {
        return pagingDataSource.getDiscoverMovies()
    }

    @ExperimentalPagingApi
    override fun getDiscoverTvMediator(): LiveData<PagingData<MovieEntity>> {
        return pagingDataSource.getDiscoverTvs()
    }

    override fun addBookmarkMovie(movie: MovieBookmark) {
        localDataSource.addBookmarkMovie(movie)
    }

    override fun deleteBookmarkMovie(movieId: Int) {
        localDataSource.removeBookmarkMovie(movieId)
    }

    override fun getBookmarkMovie(movieId: Int): LiveData<MovieBookmark> {
        return localDataSource.getMovieBookmarked(movieId)
    }

    override fun getBookmarkedMovies(): LiveData<PagingData<MovieBookmark>> {
        return pagingDataSource.getBookmarkedMovies()
    }

    override fun getBookmarkedTvs(): LiveData<PagingData<MovieBookmark>> {
        return pagingDataSource.getBookmarkedTvs()
    }
}