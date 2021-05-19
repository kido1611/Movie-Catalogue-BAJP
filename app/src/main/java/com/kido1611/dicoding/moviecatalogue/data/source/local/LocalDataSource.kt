package com.kido1611.dicoding.moviecatalogue.data.source.local

import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieBookmark
import com.kido1611.dicoding.moviecatalogue.utils.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val database: TMDBDatabase,
    private val appExecutors: AppExecutors
) {
    private fun getDatabase(): TMDBDatabase = database

    private fun getMovieDao(): MovieDao = getDatabase().movieDao()

    fun getTvs() = getMovieDao().getTvs()

    fun getMovieById(movieId: Int) = getMovieDao().getMovieById(movieId)

    fun updateMovieGenre(movieId: Int, genre: String?) {
        getMovieDao().updateGenreMovie(movieId, genre)
    }

    fun addBookmarkMovie(movie: MovieBookmark) {
        appExecutors.diskIO().execute {
            getMovieDao().insertMovieBookmark(movie)
        }
    }

    fun removeBookmarkMovie(movieId: Int) {
        appExecutors.diskIO().execute {
            getMovieDao().deleteMovieBookmarkedById(movieId)
        }
    }

    fun getMovieBookmarked(movieId: Int) = getMovieDao().getMovieBookmarkById(movieId)

    fun getBookmarkedMovies() = getMovieDao().getBookmarkedMovies()

    fun getBookmarkedTvs() = getMovieDao().getBookmarkedTvs()
}