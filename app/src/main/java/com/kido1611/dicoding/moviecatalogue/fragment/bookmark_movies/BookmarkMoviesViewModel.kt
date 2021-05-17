package com.kido1611.dicoding.moviecatalogue.fragment.bookmark_movies

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kido1611.dicoding.moviecatalogue.data.source.MovieRepository
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkMoviesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _query = MutableLiveData<String>()

    @ExperimentalPagingApi
    fun list(): LiveData<PagingData<MovieBookmark>> = Transformations.switchMap(_query) {
        repository.getBookmarkedMovies()
            .cachedIn(viewModelScope)
    }

    fun loadList() {
        if (_query.value == "load") {
            return
        }

        _query.value = "load"
    }
}