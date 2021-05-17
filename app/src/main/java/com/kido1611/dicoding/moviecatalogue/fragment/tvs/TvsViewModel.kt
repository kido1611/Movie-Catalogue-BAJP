package com.kido1611.dicoding.moviecatalogue.fragment.tvs

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kido1611.dicoding.moviecatalogue.data.source.MovieRepository
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvsViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _query = MutableLiveData<String>()

    @ExperimentalPagingApi
    fun list(): LiveData<PagingData<MovieEntity>> = Transformations.switchMap(_query) {
        repository.getDiscoverTvMediator()
            .cachedIn(viewModelScope)
    }

    fun loadList() {
        if (_query.value == "load") {
            return
        }

        _query.value = "load"
    }
}