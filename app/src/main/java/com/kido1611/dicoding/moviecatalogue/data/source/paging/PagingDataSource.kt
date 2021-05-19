package com.kido1611.dicoding.moviecatalogue.data.source.paging

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.kido1611.dicoding.moviecatalogue.data.source.local.TMDBDatabase
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieBookmark
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.kido1611.dicoding.moviecatalogue.data.source.remote.TMDBService
import com.kido1611.dicoding.moviecatalogue.data.source.remote.mediator.DiscoverMediator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PagingDataSource @Inject constructor(
    private val service: TMDBService,
    private val database: TMDBDatabase
) {

    @ExperimentalPagingApi
    fun getDiscoverMovies(): LiveData<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = DiscoverMediator(
                true, database, service
            )
        ) {
            database.movieDao().getMovies()
        }
            .liveData
    }

    @ExperimentalPagingApi
    fun getDiscoverTvs(): LiveData<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = DiscoverMediator(
                false, database, service
            )
        ) {
            database.movieDao().getTvs()
        }
            .liveData
    }

    fun getBookmarkedMovies(): LiveData<PagingData<MovieBookmark>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                database.movieDao().getBookmarkedMovies()
            }
        )
            .liveData
    }

    fun getBookmarkedTvs(): LiveData<PagingData<MovieBookmark>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                database.movieDao().getBookmarkedTvs()
            }
        )
            .liveData
    }
}