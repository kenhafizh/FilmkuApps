package com.example.filmkuapps.data.remote.dto

import com.example.filmkuapps.domain.model.MovieCast
import com.example.filmkuapps.domain.model.MovieCredits
import com.example.filmkuapps.domain.model.MovieCrew

// ==============================
// DATA LAYER / DTO
// Response dari API
// ==============================

data class MovieCreditsDto(
    val id: Int = 0,
    val cast: List<MovieCastDto> = emptyList(),
    val crew: List<MovieCrewDto> = emptyList()
) {
    fun toDomain(): MovieCredits {
        return MovieCredits(
            id = id,
            cast = cast.map { it.toDomain() },
            crew = crew.map { it.toDomain() }
        )
    }
}

data class MovieCastDto(
    val adult: Boolean = false,
    val gender: Int = 0,
    val id: Int = 0,
    val known_for_department: String = "",
    val name: String = "",
    val original_name: String = "",
    val popularity: Double = 0.0,
    val profile_path: String? = null,
    val cast_id: Int = 0,
    val character: String = "",
    val credit_id: String = "",
    val order: Int = 0
) {
    fun toDomain(): MovieCast {
        return MovieCast(
            adult = adult,
            gender = gender,
            id = id,
            knownForDepartment = known_for_department,
            name = name,
            originalName = original_name,
            popularity = popularity,
            profilePath = profile_path,
            castId = cast_id,
            character = character,
            creditId = credit_id,
            order = order
        )
    }
}

data class MovieCrewDto(
    val adult: Boolean = false,
    val gender: Int = 0,
    val id: Int = 0,
    val known_for_department: String = "",
    val name: String = "",
    val original_name: String = "",
    val popularity: Double = 0.0,
    val profile_path: String? = null,
    val credit_id: String = "",
    val department: String = "",
    val job: String = ""
) {
    fun toDomain(): MovieCrew {
        return MovieCrew(
            adult = adult,
            gender = gender,
            id = id,
            knownForDepartment = known_for_department,
            name = name,
            originalName = original_name,
            popularity = popularity,
            profilePath = profile_path,
            creditId = credit_id,
            department = department,
            job = job
        )
    }
}