package com.kido1611.dicoding.moviecatalogue.holder

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kido1611.dicoding.moviecatalogue.R
import com.kido1611.dicoding.moviecatalogue.activity.detail.DetailActivity
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.kido1611.dicoding.moviecatalogue.data.source.remote.entity.MovieResponse
import com.kido1611.dicoding.moviecatalogue.databinding.MovieListViewBinding
import com.kido1611.dicoding.moviecatalogue.extension.loadImageFromTMDB
import com.kido1611.dicoding.moviecatalogue.extension.toReadableDateFormat

class MovieListViewHolder(
    private val binding: MovieListViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieResponse) {
        binding.apply {
            tvTitle.text = movie.getMovieTitle()
            tvRating.text = root.context.getString(
                R.string.rating_placeholder,
                movie.vote_average.toString()
            )
            tvDescription.text = movie.overview
            tvReleaseDate.text = root.context.getString(
                R.string.release_at_placeholder,
                movie.getMovieReleaseDate()?.toReadableDateFormat()
            )

            ivPoster.loadImageFromTMDB(movie.poster_path)
        }
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movie.id)
            intent.putExtra(DetailActivity.EXTRA_IS_MOVIE, movie.isMovie())

            itemView.context.startActivity(intent)
        }
    }

    fun bind(movie: MovieEntity) {
        binding.apply {
            tvTitle.text = movie.getMovieTitle()
            tvRating.text = root.context.getString(
                R.string.rating_placeholder,
                movie.vote_average.toString()
            )
            tvDescription.text = movie.overview
            tvReleaseDate.text = root.context.getString(
                R.string.release_at_placeholder,
                movie.getMovieReleaseDate()?.toReadableDateFormat()
            )

            ivPoster.loadImageFromTMDB(movie.poster_path)
        }
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movie.movie_id)
            intent.putExtra(DetailActivity.EXTRA_IS_MOVIE, movie.isMovie())

            itemView.context.startActivity(intent)
        }
    }

    companion object {
        fun create(parent: ViewGroup): MovieListViewHolder {
            val binding = MovieListViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return MovieListViewHolder(binding)
        }
    }
}