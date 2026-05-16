package com.example.filmkuapps.domain.usecase

import com.example.filmkuapps.domain.model.Review
import com.example.filmkuapps.domain.repository.MovieRepository

class ReviewMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: Int): List<Review> = repository.getMoviewReview(movieId = movieId)
}