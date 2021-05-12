package com.kido1611.dicoding.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import com.kido1611.dicoding.moviecatalogue.data.source.remote.RemoteDataSource
import com.kido1611.dicoding.moviecatalogue.model.Movie

class FakeMovieRepository(
    private val remoteDataSource: RemoteDataSource
) : MovieDataSource {
    override fun getDiscoverMovie(): LiveData<UIState<List<Movie>>> {
        return remoteDataSource.getDiscoverMovie()
    }

    override fun getDiscoverTv(): LiveData<UIState<List<Movie>>> {
        return remoteDataSource.getDiscoverTv()
    }

    override fun getMovieById(id: Int): LiveData<UIState<Movie>> {
        return remoteDataSource.getMovieById(id)
    }

    override fun getTvById(id: Int): LiveData<UIState<Movie>> {
        return remoteDataSource.getTvById(id)
    }
}