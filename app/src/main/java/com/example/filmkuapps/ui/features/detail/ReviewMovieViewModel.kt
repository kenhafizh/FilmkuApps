package com.example.filmkuapps.ui.features.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmkuapps.di.AppModule
import com.example.filmkuapps.domain.model.MovieDetail
import com.example.filmkuapps.domain.model.Review
import com.example.filmkuapps.domain.usecase.ReviewMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface ReviewMovieState {
    object Loading : ReviewMovieState
    data class Success(val movies: List<Review>) : ReviewMovieState
    data class Error(val message: String) : ReviewMovieState
}

class ReviewMovieViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _reviewMovieState = MutableStateFlow<ReviewMovieState>(ReviewMovieState.Loading)
    val reviewMovieState: StateFlow<ReviewMovieState> = _reviewMovieState
    private val reviewMovieUseCase: ReviewMoviesUseCase = AppModule.reviewMoviesUseCase

    init {
        val filmIdString: String? = savedStateHandle["filmId"]
        val movieId = filmIdString?.toIntOrNull() ?: 0

        if (movieId > 0) {
            fetchMovieReview(movieId)
        } else {
            _reviewMovieState.value = ReviewMovieState.Error("ID Film tidak valid")
        }
    }

    fun fetchMovieReview(id: Int) {
        viewModelScope.launch {
            _reviewMovieState.value = ReviewMovieState.Loading
            try {
                val result = reviewMovieUseCase(movieId = id)
                _reviewMovieState.value = ReviewMovieState.Success(result)
            } catch (e: Exception) {
                _reviewMovieState.value = ReviewMovieState.Error(e.message ?: "Unknown error")
            }
        }
    }

}