package com.kido1611.dicoding.moviecatalogue.activity.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.kido1611.dicoding.moviecatalogue.R
import com.kido1611.dicoding.moviecatalogue.databinding.ActivityBookmarkBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkActivity : AppCompatActivity() {
    private var binding: ActivityBookmarkBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityBinding = ActivityBookmarkBinding.inflate(
            LayoutInflater.from(this)
        )
        binding = activityBinding
        setContentView(activityBinding.root)

        val adapter = BookmarkFragmentAdapter(this)
        binding?.apply {
            toolbar.setNavigationOnClickListener {
                finish()
            }

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
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}