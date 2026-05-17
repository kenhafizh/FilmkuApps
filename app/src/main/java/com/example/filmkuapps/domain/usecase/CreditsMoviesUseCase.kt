package com.example.filmkuapps.domain.usecase

import com.example.filmkuapps.domain.model.MovieCast
import com.example.filmkuapps.domain.model.MovieCredits
import com.example.filmkuapps.domain.model.Review
import com.example.filmkuapps.domain.repository.MovieRepository

class CreditsMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: Int): List<MovieCast> = repository.getMovieCreadit(movieId = movieId)
}