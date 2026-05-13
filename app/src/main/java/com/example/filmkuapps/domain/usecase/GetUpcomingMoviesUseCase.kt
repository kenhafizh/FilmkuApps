package com.example.filmkuapps.domain.usecase

import com.example.filmkuapps.domain.model.Movie
import com.example.filmkuapps.domain.repository.MovieRepository

class GetUpcomingMoviesUseCase (private val repository: MovieRepository) {
    suspend operator fun invoke(): List<Movie> = repository.getUpcomingMovies()
}