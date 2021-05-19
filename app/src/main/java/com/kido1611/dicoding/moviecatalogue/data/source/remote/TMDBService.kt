package com.kido1611.dicoding.moviecatalogue.data.source.remote

import com.kido1611.dicoding.moviecatalogue.data.source.remote.entity.DiscoverResponse
import com.kido1611.dicoding.moviecatalogue.data.source.remote.entity.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {
    @GET("discover/movie")
    suspend fun getDiscoverMovieNoCall(
        @Query("page") page: Int
    ): DiscoverResponse

    @GET("discover/tv")
    suspend fun getDiscoverTvNoCall(
        @Query("page") page: Int
    ): DiscoverResponse

    @GET("movie/{id}")
    fun getMovieById(
        @Path("id") id: Int
    ): Call<MovieResponse>

    @GET("tv/{id}")
    fun getTvById(
        @Path("id") id: Int
    ): Call<MovieResponse>

    @GET("movie/{id}")
    suspend fun getMovieByIdNoCall(
        @Path("id") id: Int
    ): MovieResponse
}