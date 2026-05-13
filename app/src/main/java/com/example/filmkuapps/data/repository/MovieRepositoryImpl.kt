package com.example.filmkuapps.data.repository

import com.example.filmkuapps.data.remote.api.ApiService
import com.example.filmkuapps.domain.model.Movie
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
}