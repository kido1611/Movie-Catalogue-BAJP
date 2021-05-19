package com.kido1611.dicoding.moviecatalogue.fragment.movies

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.kido1611.dicoding.moviecatalogue.holder.MovieListViewHolder

class MoviePagingAdapter :
    PagingDataAdapter<MovieEntity, MovieListViewHolder>(MovieDiffUtilCallback()) {
    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder.create(parent)
    }
}

class MovieDiffUtilCallback : DiffUtil.ItemCallback<MovieEntity>() {
    override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem.movie_id == newItem.movie_id
    }

    override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem == newItem
    }

}