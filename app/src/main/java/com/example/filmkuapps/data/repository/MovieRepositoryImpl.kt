package com.example.filmkuapps.data.repository

import com.example.filmkuapps.data.remote.api.ApiService
import com.example.filmkuapps.data.remote.dto.MovieReviewResponseDto
import com.example.filmkuapps.domain.model.Movie
import com.example.filmkuapps.domain.model.MovieCast
import com.example.filmkuapps.domain.model.MovieDetail
import com.example.filmkuapps.domain.model.MoviewReviewResponse
import com.example.filmkuapps.domain.model.Review
import com.example.filmkuapps.domain.repository.MovieRepository

class MovieRepositoryImpl(private val apiService: ApiService) : MovieRepository {
    override suspend fun getPopularMovies(): List<Movie> {
        val response = apiService.getPopularMovies()
        return response.results.map { it.toDomain() }
    }

    override suspend fun getNowPlayingMovies(): List<Movie> {
        val response = apiService.getNowPlayingMovies()
        return response.results.map { it.toDomain() }
    }

    override suspend fun getUpcomingMovies(): List<Movie> {
        val response = apiService.getUpcomingMovies()
        return response.results.map { it.toDomain() }
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        val response = apiService.getTopRatedMovies()
        return response.results.map { it.toDomain() }
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetail {
        val response = apiService.getMovieDetail(movieId)
        return response.toDomain()
    }

    override suspend fun getMoviewReview(movieId: Int): List<Review> {
        val response = apiService.getMovieReviews(movieId)
        return response.results.map { it.toDomain()}
    }

    override suspend fun getMovieCreadit(movieId: Int): List<MovieCast> {
        val response = apiService.getMoviecredits(movieId)
        return response.cast.map { it.toDomain() }
    }


}