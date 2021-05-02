package com.kido1611.dicoding.moviecatalogue.activity.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.kido1611.dicoding.moviecatalogue.R
import com.kido1611.dicoding.moviecatalogue.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val adapter = FragmentAdapter(this)
        binding.apply {
            vp.adapter = adapter
            vp.offscreenPageLimit = 2
            TabLayoutMediator(tabs, vp) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(R.string.movies_title)
                    1 -> getString(R.string.tvs_title)
                    else -> ""
                }
            }
                .attach()
        }

        supportActionBar?.elevation = 0f
    }
}