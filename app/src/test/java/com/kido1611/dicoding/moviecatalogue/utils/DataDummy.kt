package com.kido1611.dicoding.moviecatalogue.utils

import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieBookmark
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.kido1611.dicoding.moviecatalogue.data.source.remote.entity.MovieResponse

object DataDummy {

    fun generateDummyMovieEntity(id: Int, isMovie: Boolean): MovieEntity {
        return MovieEntity(
            backdrop_path = "/mnDvPokXpvsdPcWSjNRPhiiLOKu.jpg",
            first_air_date = null,
            genres = null,
            movie_id = 332562 + id,
            name = if (!isMovie) "A Star Is Born" else null,
            original_language = "en",
            overview = "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
            popularity = 33.675,
            poster_path = "/wrFpXMNBRj2PBiN4Z5kix51XaIZ.jpg",
            release_date = "2018-10-03",
            title = if (isMovie) "A Star Is Born" else null,
            vote_average = 7.5,
            vote_count = 9345,
            list_id = null,
            is_movie = isMovie
        )
    }

    fun generateDummyMovieEntities(isMovie: Boolean): List<MovieEntity> {
        val list = mutableListOf<MovieEntity>()
        for (i in 0..9) {
            list.add(generateDummyMovieEntity(i, isMovie))
        }
        return list
    }

    fun generateDummyMovieBookmark(id: Int, isMovie: Boolean): MovieBookmark {
        return MovieBookmark(
            first_air_date = null,
            movie_id = 332562 + id,
            name = null,
            overview = "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
            poster_path = "/wrFpXMNBRj2PBiN4Z5kix51XaIZ.jpg",
            release_date = "2018-10-03",
            title = "A Star Is Born",
            vote_average = 7.5,
            is_movie = isMovie
        )
    }

    fun generateDummyMovieBookmarks(isMovie: Boolean): List<MovieBookmark> {
        val list = mutableListOf<MovieBookmark>()
        for (i in 0..9) {
            list.add(generateDummyMovieBookmark(i, isMovie))
        }
        return list
    }

    fun generateDummyMovieResponse(id: Int, isMovie: Boolean): MovieResponse {
        return MovieResponse(
            backdrop_path = "/mnDvPokXpvsdPcWSjNRPhiiLOKu.jpg",
            first_air_date = null,
            genres = null,
            id = 332562 + id,
            name = if (!isMovie) "A Star Is Born" else null,
            original_language = "en",
            overview = "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
            popularity = 33.675,
            poster_path = "/wrFpXMNBRj2PBiN4Z5kix51XaIZ.jpg",
            release_date = "2018-10-03",
            title = if (isMovie) "A Star Is Born" else null,
            vote_average = 7.5,
            vote_count = 9345
        )
    }
}