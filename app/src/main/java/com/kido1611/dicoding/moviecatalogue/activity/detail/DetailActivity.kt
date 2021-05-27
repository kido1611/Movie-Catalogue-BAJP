package com.kido1611.dicoding.moviecatalogue.activity.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import com.kido1611.dicoding.moviecatalogue.R
import com.kido1611.dicoding.moviecatalogue.data.source.UIState
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieBookmark
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.kido1611.dicoding.moviecatalogue.databinding.ActivityDetailBinding
import com.kido1611.dicoding.moviecatalogue.extension.loadImageFromTMDB
import com.kido1611.dicoding.moviecatalogue.extension.toReadableDateFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private var binding: ActivityDetailBinding? = null
    private lateinit var currentMovie: MovieEntity
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityBinding = ActivityDetailBinding.inflate(layoutInflater)
        binding = activityBinding
        setContentView(activityBinding.root)

        binding?.apply {
            toolbar.apply {
                setNavigationOnClickListener {
                    onBackPressed()
                }
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.share_menu -> {
                            ShareCompat
                                .IntentBuilder
                                .from(this@DetailActivity)
                                .setType("text/plain")
                                .setChooserTitle(R.string.share_now)
                                .setText(
                                    getString(
                                        R.string.share_movie_placeholder,
                                        currentMovie.getMovieTitle()
                                    )
                                )
                                .startChooser()
                            true
                        }
                        else -> false
                    }
                }
            }

            fabFavorite.setOnClickListener {
                viewModel.toggleBookmark(
                    MovieBookmark(
                        first_air_date = currentMovie.first_air_date,
                        is_movie = currentMovie.is_movie,
                        name = currentMovie.name,
                        movie_id = currentMovie.movie_id,
                        poster_path = currentMovie.poster_path,
                        release_date = currentMovie.release_date,
                        title = currentMovie.title,
                        vote_average = currentMovie.vote_average,
                        overview = currentMovie.overview
                    )
                )
            }
        }

        val isMovie = intent.extras?.getBoolean(EXTRA_IS_MOVIE, true) ?: true
        val movieId = intent.extras?.getInt(EXTRA_MOVIE_ID, 0) ?: 0

        viewModel.setMovie(isMovie, movieId)

        viewModel.getMovie()
            .observe(this) {
                when (it) {
                    is UIState.Error -> {
                        if (it.data != null) {
                            showSuccess()
                            initView(it.data)

                            Toast.makeText(
                                this,
                                getString(R.string.failed_update_data),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        } else {
                            showError(it.message)
                        }
                    }
                    is UIState.Success -> {
                        showSuccess()
                        initView(it.data)
                    }
                    is UIState.Loading -> {
                        if (it.data != null) {
                            showSuccess()
                            initView(it.data)
                        } else {
                            showLoading()
                        }
                    }
                }
            }

        viewModel.getBookmarkMovie().observe(this) {
            viewModel.setIsBookmarked(it != null)
            updateFabFavorite(it != null)
        }
    }

    private fun initView(movie: MovieEntity) {
        currentMovie = movie

        binding?.apply {
            appbar.setExpanded(true)
            ivBackdrop.loadImageFromTMDB(movie.backdrop_path)
            if (toolbar.menu.findItem(R.id.share_menu) == null) {
                toolbar.inflateMenu(R.menu.detail_menu)
            }

            contentDetail.apply {
                ivPoster.loadImageFromTMDB(movie.poster_path)

                tvTitle.text = movie.getMovieTitle()
                tvReleaseDate.text =
                    getString(
                        R.string.release_at_placeholder,
                        movie.getMovieReleaseDate()?.toReadableDateFormat()
                    )
                tvRating.text =
                    getString(R.string.rating_placeholder, movie.vote_average.toString())
                tvLanguage.text = getString(R.string.language_placeholder, movie.original_language)

                tvGenre.text = movie.genres

                tvDescription.text = movie.overview
            }
        }
    }

    private fun updateFabFavorite(isBookmarked: Boolean) {
        binding?.fabFavorite?.setImageResource(
            if (isBookmarked) {
                R.drawable.ic_baseline_favorite_24_white
            } else {
                R.drawable.ic_baseline_favorite_border_24_white
            }
        )
        binding?.fabFavorite?.tag = if (isBookmarked) {
            "bookmarked"
        } else {
            "un-bookmarked"
        }
    }

    private fun showSuccess() {
        binding?.apply {
            groupError.isVisible = false
            progressBar.isVisible = false
            contentDetail.root.isVisible = true
        }
    }

    private fun showLoading() {
        binding?.apply {
            groupError.isVisible = false
            progressBar.isVisible = true
            contentDetail.root.isVisible = false
        }
    }

    private fun showError(message: String) {
        binding?.apply {
            groupError.isVisible = true
            progressBar.isVisible = false
            contentDetail.root.isVisible = false

            tvMessage.text = message
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val EXTRA_IS_MOVIE = "extra_is_movie"
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }

}