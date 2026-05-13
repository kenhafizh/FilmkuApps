package com.example.filmkuapps.data.remote.dto
import com.example.filmkuapps.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    val results: List<MovieDto>
)

data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("release_date")
    val releaseDate: String
) {
    // Mapper: Mengubah DTO ke Model Domain + Menambah Base URL Gambar
    fun toDomain(): Movie {
        return Movie(
            id = id,
            title = title,
            overview = overview,
            posterUrl = "https://image.tmdb.org/t/p/w500$posterPath",
            rating = voteAverage,
            releaseDate = releaseDate
        )
    }
}