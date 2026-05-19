package com.example.filmkuapps.data.remote.dto

import com.example.filmkuapps.domain.model.MovieSearch
import com.example.filmkuapps.domain.model.MovieSearchResponse

// ==============================
// DATA LAYER / DTO
// Response dari API
// ==============================

data class MovieSearchResponseDto(
    val page: Int = 0,
    val results: List<MovieSearchDto> = emptyList(),
    val total_pages: Int = 0,
    val total_results: Int = 0
) {
    fun toDomain(): MovieSearchResponse {
        return MovieSearchResponse(
            page = page,
            results = results.map { it.toDomain() },
            totalPages = total_pages,
            totalResults = total_results
        )
    }
}

data class MovieSearchDto(
    val adult: Boolean = false,
    val backdrop_path: String? = null,
    val genre_ids: List<Int> = emptyList(),
    val id: Int = 0,
    val title: String = "",
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String? = null,
    val release_date: String = "",
    val softcore: Boolean = false,
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
) {
    fun toDomain(): MovieSearch {
        return MovieSearch(
            adult = adult,
            backdropPath = backdrop_path,
            genreIds = genre_ids,
            id = id,
            title = title,
            originalLanguage = original_language,
            originalTitle = original_title,
            overview = overview,
            popularity = popularity,
            posterPath = poster_path,
            releaseDate = release_date,
            softcore = softcore,
            video = video,
            voteAverage = vote_average,
            voteCount = vote_count
        )
    }
}