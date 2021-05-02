package com.kido1611.dicoding.moviecatalogue.fragment.movies

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kido1611.dicoding.moviecatalogue.holder.MovieListViewHolder
import com.kido1611.dicoding.moviecatalogue.model.Movie

class MovieListAdapter : RecyclerView.Adapter<MovieListViewHolder>() {

    private val movieList = mutableListOf<Movie>()

    fun setMovieList(list: List<Movie>) {
        movieList.clear()
        movieList.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val item = movieList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = movieList.size
}