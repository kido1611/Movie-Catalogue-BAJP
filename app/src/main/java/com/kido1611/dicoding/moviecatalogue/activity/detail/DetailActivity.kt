package com.kido1611.dicoding.moviecatalogue.activity.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import com.kido1611.dicoding.moviecatalogue.R
import com.kido1611.dicoding.moviecatalogue.data.source.UIState
import com.kido1611.dicoding.moviecatalogue.databinding.ActivityDetailBinding
import com.kido1611.dicoding.moviecatalogue.extension.loadImageFromTMDB
import com.kido1611.dicoding.moviecatalogue.extension.toReadableDateFormat
import com.kido1611.dicoding.moviecatalogue.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_IS_MOVIE = "extra_is_movie"
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var currentMovie: Movie
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
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
        }

        val isMovie = intent.extras?.getBoolean(EXTRA_IS_MOVIE, true) ?: true
        val movieId = intent.extras?.getInt(EXTRA_MOVIE_ID, 0) ?: 0

        viewModel.setMovie(isMovie, movieId)

        viewModel.getMovie()
            .observe(this) {
                when (it) {
                    is UIState.Error -> {
                        showError(it.message)
                    }
                    UIState.Loading -> {
                        showLoading()
                    }
                    is UIState.Success -> {
                        showSuccess()
                        initView(it.data)
                    }
                }
            }
    }

    private fun initView(movie: Movie) {
        currentMovie = movie

        binding.apply {
            binding.appbar.setExpanded(true)
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

                tvGenre.text = movie.genres?.joinToString {
                    it.name
                }

                tvDescription.text = movie.overview
            }
        }
    }

    private fun showSuccess() {
        binding.apply {
            groupError.isVisible = false
            progressBar.isVisible = false
            contentDetail.root.isVisible = true
        }
    }

    private fun showLoading() {
        binding.apply {
            groupError.isVisible = false
            progressBar.isVisible = true
            contentDetail.root.isVisible = false
        }
    }

    private fun showError(message: String) {
        binding.apply {
            groupError.isVisible = true
            progressBar.isVisible = false
            contentDetail.root.isVisible = false

            tvMessage.text = message
        }
    }
}