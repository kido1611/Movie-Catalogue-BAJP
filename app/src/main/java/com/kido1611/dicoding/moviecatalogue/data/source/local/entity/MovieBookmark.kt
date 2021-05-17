package com.kido1611.dicoding.moviecatalogue.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "bookmark_movies"
)
data class MovieBookmark(
    var first_air_date: String?,
    val is_movie: Boolean,
    val name: String?,
    @PrimaryKey
    val movie_id: Int,
    val poster_path: String?,
    var release_date: String?,
    val title: String?,
    var vote_average: Double?
)
