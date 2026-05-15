package com.example.filmkuapps.domain.usecase

import com.example.filmkuapps.domain.model.Movie
import com.example.filmkuapps.domain.model.MovieDetail
import com.example.filmkuapps.domain.repository.MovieRepository

class DetailMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: Int): MovieDetail = repository.getMovieDetail(movieId = movieId)
}