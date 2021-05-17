package com.kido1611.dicoding.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieRemoteKey

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(list: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity)

    @Query("select * from movies where is_movie = 1 order by list_id asc")
    fun getMovies(): PagingSource<Int, MovieEntity>

    @Query("select * from movies where is_movie = 0 order by list_id asc")
    fun getTvs(): PagingSource<Int, MovieEntity>

    @Query("select * from movies where movie_id = :id")
    fun getMovieById(id: Int): LiveData<MovieEntity>

    @Query("select * from movies where movie_id = :id")
    suspend fun getMovieByIdSuspend(id: Int): MovieEntity

    @Update(
        entity = MovieEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    fun updateMovie(movie: MovieEntity)

    @Query("update movies set genres=:genre where movie_id = :id")
    fun updateGenreMovie(id: Int, genre: String?)

    @Query("delete from movies where is_movie = 1")
    fun deleteMovies()

    @Query("delete from movies where is_movie = 0")
    fun deleteTvs()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRemoteKeys(list: List<MovieRemoteKey>)

    @Query("select * from movie_remote_keys where id = :id")
    fun getRemoteKeyByMovieId(id: Int): MovieRemoteKey?

    @Query("delete from movie_remote_keys where is_movie = 1")
    fun deleteMovieRemoteKeys()

    @Query("delete from movie_remote_keys where is_movie = 0")
    fun deleteTvRemoteKeys()
}