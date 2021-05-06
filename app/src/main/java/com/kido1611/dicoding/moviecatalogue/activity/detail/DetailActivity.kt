package com.kido1611.dicoding.moviecatalogue.activity.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.kido1611.dicoding.moviecatalogue.R
import com.kido1611.dicoding.moviecatalogue.databinding.ActivityDetailBinding
import com.kido1611.dicoding.moviecatalogue.extension.loadImageFromTMDB
import com.kido1611.dicoding.moviecatalogue.model.Movie

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_IS_MOVIE = "extra_is_movie"
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var currentMovie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.apply {
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

        val isMovie = intent.extras?.getBoolean(EXTRA_IS_MOVIE, true) ?: true
        val movieId = intent.extras?.getInt(EXTRA_MOVIE_ID, 0) ?: 0

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        viewModel.setMovie(isMovie, movieId)

        val selectedMovie = viewModel.getMovie()
        if (selectedMovie != null) {
            initView(selectedMovie)
        }
    }

    private fun initView(movie: Movie) {
        currentMovie = movie

        binding.apply {
            if (toolbar.menu.findItem(R.id.share_menu) == null) {
                toolbar.inflateMenu(R.menu.detail_menu)
            }

            toolbar.title = movie.getMovieTitle()

            contentDetail.apply {
                ivPoster.loadImageFromTMDB(movie.poster_path)

                tvTitle.text = movie.getMovieTitle()
                tvReleaseDate.text =
                    getString(R.string.release_at_placeholder, movie.getMovieReleaseDate())
                tvRating.text =
                    getString(R.string.rating_placeholder, movie.vote_average.toString())
                tvLanguage.text = getString(R.string.language_placeholder, movie.original_language)

                tvDescription.text = movie.overview
            }
        }
    }
}