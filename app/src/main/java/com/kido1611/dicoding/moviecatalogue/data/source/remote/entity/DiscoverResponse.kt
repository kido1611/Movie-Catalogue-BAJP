package com.kido1611.dicoding.moviecatalogue.data.source.remote.entity

data class DiscoverResponse(
    var page: Int,
    var results: List<MovieResponse>,
    var total_pages: Int,
    var total_results: Int
)
