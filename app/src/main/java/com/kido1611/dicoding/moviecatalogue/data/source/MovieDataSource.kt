package com.kido1611.dicoding.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity

interface MovieDataSource {
    fun getDiscoverMovieMediator(): LiveData<PagingData<MovieEntity>>
    fun getDiscoverTvMediator(): LiveData<PagingData<MovieEntity>>

    fun getMovieById(id: Int): LiveData<UIState<MovieEntity>>
    fun getTvById(id: Int): LiveData<UIState<MovieEntity>>
}

