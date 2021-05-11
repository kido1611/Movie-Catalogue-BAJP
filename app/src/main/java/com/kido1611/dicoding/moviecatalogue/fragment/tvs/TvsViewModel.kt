package com.kido1611.dicoding.moviecatalogue.fragment.tvs

import androidx.lifecycle.ViewModel
import com.kido1611.dicoding.moviecatalogue.data.source.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvsViewModel @Inject constructor(
    repository: MovieRepository
) : ViewModel() {

    val list = repository.getDiscoverTv()
}