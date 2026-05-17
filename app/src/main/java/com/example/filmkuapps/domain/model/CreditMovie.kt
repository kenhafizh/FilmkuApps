package com.example.filmkuapps.domain.model// ==============================
// DOMAIN LAYER
// Data bersih untuk UI / Compose
// ==============================

data class MovieCredits(
    val id: Int,
    val cast: List<MovieCast>,
    val crew: List<MovieCrew>
)

data class MovieCast(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?,
    val castId: Int,
    val character: String,
    val creditId: String,
    val order: Int
)

data class MovieCrew(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?,
    val creditId: String,
    val department: String,
    val job: String
)