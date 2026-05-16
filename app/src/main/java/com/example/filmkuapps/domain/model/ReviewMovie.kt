package com.example.filmkuapps.domain.model

// ==============================
// DOMAIN LAYER
// Data bersih yang dipakai UI / Compose
// ==============================

data class MoviewReviewResponse(
    val id: Int,
    val page: Int,
    val results: List<Review>,
    val totalPages: Int,
    val totalResults: Int
)

data class Review(
    val author: String,
    val authorDetails: AuthorDetails,
    val content: String,
    val createdAt: String,
    val id: String,
    val updatedAt: String,
    val url: String
)

data class AuthorDetails(
    val name: String,
    val username: String,
    val avatarPath: String?,
    val rating: Double?
)