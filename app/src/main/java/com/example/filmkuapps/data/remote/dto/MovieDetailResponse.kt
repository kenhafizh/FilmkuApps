package com.example.filmkuapps.data.remote.dto

import com.example.filmkuapps.domain.model.MovieDetail
import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    val runtime: Int, // Dalam menit (contoh: 93)
    val genres: List<GenreDto> // Daftar objek genre
) {
    fun toDomain(): MovieDetail {
        return MovieDetail(
            id = id,
            title = title,
            overview = overview,
            posterUrl = "https://image.tmdb.org/t/p/w500$posterPath",
            backdropUrl = "https://image.tmdb.org/t/p/w1280$backdropPath", // Backdrop biasanya butuh resolusi lebih besar
            releaseDate = releaseDate,
            rating = voteAverage,
            duration = "$runtime Minutes", // Kita format langsung di sini
            genres = genres.map { it.name } // Ambil namanya saja: ["Family", "Comedy", ...]
        )
    }
}

data class GenreDto(
    val id: Int,
    val name: String
)