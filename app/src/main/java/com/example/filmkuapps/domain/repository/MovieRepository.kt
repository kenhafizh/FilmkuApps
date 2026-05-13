package com.example.filmkuapps.domain.repository

import com.example.filmkuapps.domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getNowPlayingMovies(): List<Movie>
    suspend fun getUpcomingMovies(): List<Movie>
    suspend fun getTopRatedMovies(): List<Movie>

}