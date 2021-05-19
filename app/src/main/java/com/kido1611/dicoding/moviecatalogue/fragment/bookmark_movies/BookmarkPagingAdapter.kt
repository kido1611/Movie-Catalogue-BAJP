package com.kido1611.dicoding.moviecatalogue.fragment.bookmark_movies

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieBookmark
import com.kido1611.dicoding.moviecatalogue.holder.MovieListViewHolder

class BookmarkPagingAdapter :
    PagingDataAdapter<MovieBookmark, MovieListViewHolder>(MovieBookmarkDiffUtilCallback()) {
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

class MovieBookmarkDiffUtilCallback : DiffUtil.ItemCallback<MovieBookmark>() {
    override fun areItemsTheSame(oldItem: MovieBookmark, newItem: MovieBookmark): Boolean {
        return oldItem.movie_id == newItem.movie_id
    }

    override fun areContentsTheSame(oldItem: MovieBookmark, newItem: MovieBookmark): Boolean {
        return oldItem == newItem
    }
}