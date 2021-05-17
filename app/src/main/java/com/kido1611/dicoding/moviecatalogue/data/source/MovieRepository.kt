package com.kido1611.dicoding.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.kido1611.dicoding.moviecatalogue.data.source.local.TMDBDatabase
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.kido1611.dicoding.moviecatalogue.data.source.remote.ApiResponse
import com.kido1611.dicoding.moviecatalogue.data.source.remote.RemoteDataSource
import com.kido1611.dicoding.moviecatalogue.data.source.remote.TMDBService
import com.kido1611.dicoding.moviecatalogue.data.source.remote.entity.MovieResponse
import com.kido1611.dicoding.moviecatalogue.data.source.remote.mediator.DiscoverMediator
import com.kido1611.dicoding.moviecatalogue.utils.AppExecutors
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val database: TMDBDatabase,
    private val service: TMDBService,
    private val appExecutors: AppExecutors,
    private val remoteDataSource: RemoteDataSource
) : MovieDataSource {
    override fun getMovieById(id: Int): LiveData<UIState<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> {
                return database.movieDao().getMovieById(id)
            }

            override fun shouldFetch(data: MovieEntity?): Boolean = true

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> =
                remoteDataSource.getMovieById(id)

            override fun saveCallResult(data: MovieResponse) {
                val entity = data.toMovieEntity()
                database.movieDao().updateGenreMovie(id, entity.genres)
            }
        }
            .asLiveData()
    }

    override fun getTvById(id: Int): LiveData<UIState<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> = database.movieDao().getMovieById(id)

            override fun shouldFetch(data: MovieEntity?): Boolean = true

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> =
                remoteDataSource.getTvById(id)

            override fun saveCallResult(data: MovieResponse) {
                val entity = data.toMovieEntity()
                database.movieDao().updateGenreMovie(id, entity.genres)
            }
        }
            .asLiveData()
    }


    @ExperimentalPagingApi
    override fun getDiscoverMovieMediator(): LiveData<PagingData<MovieEntity>> {
        val movieDao = database.movieDao()
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = DiscoverMediator(
                true, database, service
            )
        ) {
            movieDao.getMovies()
        }
            .liveData
    }

    @ExperimentalPagingApi
    override fun getDiscoverTvMediator(): LiveData<PagingData<MovieEntity>> {
        val movieDao = database.movieDao()
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = DiscoverMediator(
                false, database, service
            )
        ) {
            movieDao.getTvs()
        }
            .liveData
    }
}