package com.kido1611.dicoding.moviecatalogue.model

data class Movie(
    var backdrop_path: String?,
    var first_air_date: String?,        // TV
    var genre_ids: List<Int>?,
    var id: Int,
    var name: String?,                  // TV
    var original_language: String?,
    var overview: String?,
    var popularity: Double?,
    var poster_path: String?,
    var release_date: String?,          // Movie
    var title: String?,                 // Movie
    var vote_average: Double?,
    var vote_count: Int?
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
