package com.example.filmkuapps.domain.usecase

import com.example.filmkuapps.domain.model.MovieCast
import com.example.filmkuapps.domain.model.MovieCredits
import com.example.filmkuapps.domain.model.MovieSearch
import com.example.filmkuapps.domain.model.Review
import com.example.filmkuapps.domain.repository.MovieRepository

class SearchMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(keyword:String): List<MovieSearch> = repository.getSearchMovie(keywords = keyword)
}