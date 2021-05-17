package com.kido1611.dicoding.moviecatalogue.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_remote_keys"
)
data class MovieRemoteKey(
    @PrimaryKey
    val id: Int,
    val is_movie: Boolean,
    val prevKey: Int?,
    val nextKey: Int?
)