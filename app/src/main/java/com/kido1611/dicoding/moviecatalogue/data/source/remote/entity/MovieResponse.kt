package com.kido1611.dicoding.moviecatalogue.data.source.remote.entity

import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity

data class MovieResponse(
    var backdrop_path: String?,
    var first_air_date: String?,        // TV
    var genres: List<GenreResponse>?,
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

    fun toMovieEntity(): MovieEntity {
        return this.let {
            MovieEntity(
                backdrop_path = it.backdrop_path,
                first_air_date = it.first_air_date,
                genres = it.genres?.joinToString { genre ->
                    genre.name
                },
                is_movie = isMovie(),
                movie_id = it.id,
                name = it.name,
                original_language = it.original_language,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.poster_path,
                release_date = it.release_date,
                title = it.title,
                vote_average = it.vote_average,
                vote_count = it.vote_count,
                list_id = null
            )
        }
    }
}
