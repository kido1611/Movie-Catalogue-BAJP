package com.kido1611.dicoding.moviecatalogue.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "movies"
)
data class MovieEntity(
    var backdrop_path: String?,
    var first_air_date: String?,        // TV
    var genres: String?,
    var is_movie: Boolean,
    @PrimaryKey(autoGenerate = false)
    var movie_id: Int,
    var name: String?,                  // TV
    var original_language: String?,
    var overview: String?,
    var popularity: Double?,
    var poster_path: String?,
    var release_date: String?,          // Movie
    var title: String?,                 // Movie
    var vote_average: Double?,
    var vote_count: Int?,
    var list_id: Long? = null
) {

    fun isMovie(): Boolean = title != null

    fun getMovieTitle(): String? = if (isMovie()) {
        title
    } else {
        name
    }

    fun getMovieReleaseDate(): String? = if (isMovie()) {
        release_date
    } else {
        first_air_date
    }
}

