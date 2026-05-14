package com.example.filmkuapps.domain.repository

import com.example.filmkuapps.domain.model.Movie
import com.example.filmkuapps.domain.model.MovieDetail

interface MovieRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getNowPlayingMovies(): List<Movie>
    suspend fun getUpcomingMovies(): List<Movie>
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getMovieDetail(movieId: Int): MovieDetail
}