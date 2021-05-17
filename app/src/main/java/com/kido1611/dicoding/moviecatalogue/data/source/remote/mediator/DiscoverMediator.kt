package com.kido1611.dicoding.moviecatalogue.data.source.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kido1611.dicoding.moviecatalogue.data.source.local.TMDBDatabase
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieRemoteKey
import com.kido1611.dicoding.moviecatalogue.data.source.remote.TMDBService
import com.kido1611.dicoding.moviecatalogue.utils.EspressoIdlingResource

@ExperimentalPagingApi
class DiscoverMediator(
    private val isMovie: Boolean,
    private val database: TMDBDatabase,
    private val service: TMDBService
) : RemoteMediator<Int, MovieEntity>() {
    private val movieDao = database.movieDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKey?.prevKey ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKey?.nextKey ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    nextKey
                }
            }

            EspressoIdlingResource.increment()
            val response = if (isMovie) {
                service.getDiscoverMovieNoCall(page)
            } else {
                service.getDiscoverTvNoCall(page)
            }

            var index = System.currentTimeMillis()
            val newList = mutableListOf<MovieEntity>()

            response.results.forEach {
                val data = it.toMovieEntity()
                data.list_id = index
                newList.add(data)
                index++
            }
            val endOfPagination = newList.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH && !endOfPagination) {
                    if (isMovie) {
                        movieDao.deleteMovieRemoteKeys()
                        movieDao.deleteMovies()
                    } else {
                        movieDao.deleteTvRemoteKeys()
                        movieDao.deleteTvs()
                    }
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPagination) null else page + 1

                val remoteKeys = newList.map {
                    MovieRemoteKey(
                        id = it.movie_id,
                        is_movie = isMovie,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                movieDao.insertRemoteKeys(remoteKeys)
                movieDao.insertMovies(newList)
            }
            EspressoIdlingResource.decrement()
            MediatorResult.Success(
                endOfPaginationReached = endOfPagination
            )
        } catch (exception: Exception) {
            EspressoIdlingResource.decrement()
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): MovieRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movie_id?.let { movieId ->
                database.withTransaction {
                    movieDao.getRemoteKeyByMovieId(movieId)
                }
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): MovieRemoteKey? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            database.withTransaction {
                movieDao.getRemoteKeyByMovieId(movie.movie_id)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): MovieRemoteKey? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            database.withTransaction {
                movieDao.getRemoteKeyByMovieId(movie.movie_id)
            }
        }
    }
}