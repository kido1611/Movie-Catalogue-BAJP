package com.kido1611.dicoding.moviecatalogue.data.source.remote

import com.kido1611.dicoding.moviecatalogue.model.Movie
import com.kido1611.dicoding.moviecatalogue.model.RemoteDiscoverResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {
    @GET("discover/movie")
    fun getDiscoverMovie(
        @Query("page") page: Int
    ): Call<RemoteDiscoverResponse>

    @GET("discover/tv")
    fun getDiscoverTv(
        @Query("page") page: Int
    ): Call<RemoteDiscoverResponse>

    @GET("movie/{id}")
    fun getMovieById(
        @Path("id") id: Int
    ): Call<Movie>

    @GET("tv/{id}")
    fun getTvById(
        @Path("id") id: Int
    ): Call<Movie>
}