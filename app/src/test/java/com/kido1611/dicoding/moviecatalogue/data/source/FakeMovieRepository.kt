package com.kido1611.dicoding.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import com.kido1611.dicoding.moviecatalogue.data.source.remote.RemoteDataSource

class FakeMovieRepository(
    private val remoteDataSource: RemoteDataSource
) : MovieDataSource {
    override fun getDiscoverMovie(): LiveData<DiscoverUiState> {
        return remoteDataSource.getDiscoverMovie()
    }

    override fun getDiscoverTv(): LiveData<DiscoverUiState> {
        return remoteDataSource.getDiscoverTv()
    }

    override fun getMovieById(id: Int): LiveData<DetailUiState> {
        return remoteDataSource.getMovieById(id)
    }

    override fun getTvById(id: Int): LiveData<DetailUiState> {
        return remoteDataSource.getTvById(id)
    }
}