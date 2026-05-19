package com.example.filmkuapps.domain.model

// ==============================
// DOMAIN LAYER
// Data bersih untuk UI / Compose
// ==============================

data class MovieSearchResponse(
    val page: Int,
    val results: List<MovieSearch>,
    val totalPages: Int,
    val totalResults: Int
)

data class MovieSearch(
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int>,
    val id: Int,
    val title: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val softcore: Boolean,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)