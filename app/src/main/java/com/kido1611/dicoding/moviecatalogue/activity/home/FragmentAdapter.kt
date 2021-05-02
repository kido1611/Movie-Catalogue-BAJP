package com.kido1611.dicoding.moviecatalogue.activity.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kido1611.dicoding.moviecatalogue.fragment.movies.MoviesFragment
import com.kido1611.dicoding.moviecatalogue.fragment.tvs.TvsFragment

class FragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MoviesFragment.newInstance()
            1 -> TvsFragment.newInstance()
            else -> throw UnknownError("Unknown error")
        }
    }
}