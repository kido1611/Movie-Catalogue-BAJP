package com.kido1611.dicoding.moviecatalogue.fragment.tvs

import androidx.lifecycle.ViewModel
import com.kido1611.dicoding.moviecatalogue.model.Movie
import com.kido1611.dicoding.moviecatalogue.utils.DataDummy

class TvsViewModel : ViewModel() {
    fun list(): List<Movie> = DataDummy.generateDummyTv()
}