package com.kido1611.dicoding.moviecatalogue.data.source.local

import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieBookmark
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val database: TMDBDatabase
) {
    fun getDatabase(): TMDBDatabase = database

    private fun getMovieDao(): MovieDao = getDatabase().movieDao()

    fun getMovies() = getMovieDao().getMovies()

    fun getTvs() = getMovieDao().getTvs()

    fun getMovieById(movieId: Int) = getMovieDao().getMovieById(movieId)

    fun updateMovieGenre(movieId: Int, genre: String?) {
        getMovieDao().updateGenreMovie(movieId, genre)
    }

    fun addBookmarkMovie(movie: MovieBookmark) {
        getMovieDao().insertMovieBookmark(movie)
    }

    fun removeBookmarkMovie(movieId: Int) {
        getMovieDao().deleteMovieBookmarkedById(movieId)
    }

    fun getMovieBookmarked(movieId: Int) = getMovieDao().getMovieBookmarkById(movieId)
}