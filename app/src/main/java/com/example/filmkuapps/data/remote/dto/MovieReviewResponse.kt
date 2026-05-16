package com.example.filmkuapps.data.remote.dto

import com.example.filmkuapps.domain.model.AuthorDetails
import com.example.filmkuapps.domain.model.Review
import com.example.filmkuapps.domain.model.MoviewReviewResponse

// ==============================
// DATA LAYER / DTO
// Response dari API
// ==============================

data class MovieReviewResponseDto(
    val id: Int = 0,
    val page: Int = 0,
    val results: List<ReviewDto> = emptyList(),
    val total_pages: Int = 0,
    val total_results: Int = 0
) {
    fun toDomain(): MoviewReviewResponse {
        return MoviewReviewResponse(
            id = id,
            page = page,
            results = results.map { it.toDomain() },
            totalPages = total_pages,
            totalResults = total_results
        )
    }
}

data class ReviewDto(
    val author: String = "",
    val author_details: AuthorDetailsDto = AuthorDetailsDto(),
    val content: String = "",
    val created_at: String = "",
    val id: String = "",
    val updated_at: String = "",
    val url: String = ""
) {
    fun toDomain(): Review {
        return Review(
            author = author,
            authorDetails = author_details.toDomain(),
            content = content,
            createdAt = created_at,
            id = id,
            updatedAt = updated_at,
            url = url
        )
    }
}

data class AuthorDetailsDto(
    val name: String = "",
    val username: String = "",
    val avatar_path: String? = null,
    val rating: Double? = null
) {
    fun toDomain(): AuthorDetails {
        return AuthorDetails(
            name = name,
            username = username,
            avatarPath = avatar_path,
            rating = rating
        )
    }

    fun getAvatarUrl(): String {
        return if (!avatar_path.isNullOrEmpty()) {
            "https://image.tmdb.org/t/p/w45$avatar_path"
        } else {
            ""
        }
    }
}