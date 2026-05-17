package com.example.filmkuapps.domain.repository

import com.example.filmkuapps.domain.model.Movie
import com.example.filmkuapps.domain.model.MovieCast
import com.example.filmkuapps.domain.model.MovieCredits
import com.example.filmkuapps.domain.model.MovieDetail
import com.example.filmkuapps.domain.model.Review

interface MovieRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getNowPlayingMovies(): List<Movie>
    suspend fun getUpcomingMovies(): List<Movie>
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getMovieDetail(movieId: Int): MovieDetail
    suspend fun getMoviewReview(movieId: Int): List<Review>
    suspend fun getMovieCreadit(movieId: Int): List<MovieCast>
}