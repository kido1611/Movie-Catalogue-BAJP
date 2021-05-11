package com.kido1611.dicoding.moviecatalogue.model

data class RemoteDiscoverResponse(
    var page: Int,
    var results: List<Movie>,
    var total_pages: Int,
    var total_results: Int
)
