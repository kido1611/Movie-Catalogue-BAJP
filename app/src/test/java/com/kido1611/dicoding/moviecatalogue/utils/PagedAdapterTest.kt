package com.kido1611.dicoding.moviecatalogue.utils

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieBookmark
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity

class PagedAdapterTest<T : Any> : PagingDataAdapter<T, ViewHolderTest>(MovieEntityDiffUtil()) {
    override fun onBindViewHolder(holder: ViewHolderTest, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTest {
        return ViewHolderTest(View(parent.context))
    }
}

class MovieEntityDiffUtil<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        if (oldItem is MovieEntity) {
            return oldItem.movie_id == (newItem as MovieEntity).movie_id
        } else if (oldItem is MovieBookmark) {
            return oldItem.movie_id == (newItem as MovieBookmark).movie_id
        }
        return false
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}

class ViewHolderTest(view: View) : RecyclerView.ViewHolder(view)