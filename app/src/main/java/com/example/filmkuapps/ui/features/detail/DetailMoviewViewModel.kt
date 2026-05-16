package com.example.filmkuapps.ui.features.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmkuapps.di.AppModule
import com.example.filmkuapps.domain.model.MovieDetail
import com.example.filmkuapps.domain.usecase.DetailMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface DetailMovieState {
    object Loading : DetailMovieState
    data class Success(val movies: MovieDetail) : DetailMovieState
    data class Error(val message: String) : DetailMovieState
}

class DetailMoviewViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _detailMovieState = MutableStateFlow<DetailMovieState>(DetailMovieState.Loading)
    val detailMoviesState: StateFlow<DetailMovieState> = _detailMovieState
    private val getDetailMovieUseCase: DetailMoviesUseCase = AppModule.detailMoviesUseCase

    init {
        val filmIdString: String? = savedStateHandle["filmId"]
        val movieId = filmIdString?.toIntOrNull() ?: 0

        if (movieId > 0) {
            fetchMovieDetail(movieId)
        } else {
            _detailMovieState.value = DetailMovieState.Error("ID Film tidak valid")
        }
    }

    fun fetchMovieDetail(id: Int) {
        viewModelScope.launch {
            _detailMovieState.value = DetailMovieState.Loading
            try {
                val result = getDetailMovieUseCase(movieId = id)
                _detailMovieState.value = DetailMovieState.Success(result)
            } catch (e: Exception) {
                _detailMovieState.value = DetailMovieState.Error(e.message ?: "Unknown error")
            }
        }
    }

}