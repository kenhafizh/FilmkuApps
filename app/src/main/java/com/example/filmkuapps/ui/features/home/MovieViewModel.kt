package com.example.filmkuapps.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmkuapps.di.AppModule
import com.example.filmkuapps.domain.model.Movie
import com.example.filmkuapps.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.filmkuapps.domain.usecase.GetPopularMoviesUseCase
import com.example.filmkuapps.domain.usecase.GetTopRatedMoviesUseCase
import com.example.filmkuapps.domain.usecase.GetUpcomingMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface MovieUiState {
    data object Loading : MovieUiState
    data class Success(val movies: List<Movie>) : MovieUiState
    data class Error(val message: String) : MovieUiState
}

class MovieViewModel(
    // Gunakan AppModule sebagai default parameter
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase = AppModule.getPopularMovieUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase = AppModule.getNowPlayingMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase = AppModule.getUpcomingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase = AppModule.getTopRatedMoviesUseCase
) : ViewModel() {
    private val _popularMoviesState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val popularMoviesState: StateFlow<MovieUiState> = _popularMoviesState

    private val _nowPlayingState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val nowPlayingState: StateFlow<MovieUiState> = _nowPlayingState

    private val _upcomingMoviesState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val upcomingMoviesState: StateFlow<MovieUiState> = _upcomingMoviesState

    private val _topRatedMoviesState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val topRatedMoviesState: StateFlow<MovieUiState> = _topRatedMoviesState

    init {
        fetchMovies()
        fetchNowPlaying()
        fetchUpcoming()
        fetchTopRated()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            _popularMoviesState.value = MovieUiState.Loading
            try {
                val result = getPopularMoviesUseCase()
                _popularMoviesState.value = MovieUiState.Success(result)
            } catch (e: Exception) {
                _popularMoviesState.value = MovieUiState.Error(e.message ?: "Unknown error")
            }
        }
    }


    fun fetchNowPlaying() {
        viewModelScope.launch {
            _nowPlayingState.value = MovieUiState.Loading
            try {
                val result = getNowPlayingMoviesUseCase() // Panggil UseCase Upcoming
                _nowPlayingState.value = MovieUiState.Success(result)
            } catch (e: Exception) {
                _nowPlayingState.value = MovieUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchUpcoming() {
        viewModelScope.launch {
            _upcomingMoviesState.value = MovieUiState.Loading
            try {
                val result = getUpcomingMoviesUseCase() // Panggil UseCase Upcoming
                _upcomingMoviesState.value = MovieUiState.Success(result)
            } catch (e: Exception) {
                _upcomingMoviesState.value = MovieUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchTopRated() {
        viewModelScope.launch {
            _topRatedMoviesState.value = MovieUiState.Loading
            try {
                val result = getTopRatedMoviesUseCase() // Panggil UseCase Upcoming
                _topRatedMoviesState.value = MovieUiState.Success(result)
            } catch (e: Exception) {
                _topRatedMoviesState.value = MovieUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}