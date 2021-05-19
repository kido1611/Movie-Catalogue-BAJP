package com.kido1611.dicoding.moviecatalogue.activity.bookmark

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kido1611.dicoding.moviecatalogue.fragment.bookmark_movies.BookmarkMoviesFragment
import com.kido1611.dicoding.moviecatalogue.fragment.bookmark_tvs.BookmarkTvsFragment

class BookmarkFragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BookmarkMoviesFragment.newInstance()
            1 -> BookmarkTvsFragment.newInstance()
            else -> throw UnknownError("undefined position")
        }
    }

}