package com.example.filmkuapps.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val backdropUrl: String,
    val releaseDate: String,
    val rating: Double,
    val duration: String,
    val genres: List<String>
)