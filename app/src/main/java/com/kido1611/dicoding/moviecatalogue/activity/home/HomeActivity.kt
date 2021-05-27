package com.kido1611.dicoding.moviecatalogue.activity.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.kido1611.dicoding.moviecatalogue.R
import com.kido1611.dicoding.moviecatalogue.activity.bookmark.BookmarkActivity
import com.kido1611.dicoding.moviecatalogue.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private var binding: ActivityHomeBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityBinding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        binding = activityBinding
        setContentView(activityBinding.root)

        val adapter = FragmentAdapter(this)
        binding?.apply {
            vp.adapter = adapter
            vp.isUserInputEnabled = false
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_bookmark -> {
                val action = Intent(this, BookmarkActivity::class.java)
                startActivity(action)
                true
            }
            else -> {
                false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}