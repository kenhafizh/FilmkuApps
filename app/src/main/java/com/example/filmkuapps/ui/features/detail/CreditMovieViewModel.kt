package com.example.filmkuapps.ui.features.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmkuapps.di.AppModule
import com.example.filmkuapps.domain.model.MovieCast
import com.example.filmkuapps.domain.usecase.CreditsMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface CreditMovieState {
    object Loading : CreditMovieState
    data class Success(val movies: List<MovieCast>) : CreditMovieState
    data class Error(val message: String) : CreditMovieState
}

class CreditMovieViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _creditMovieState = MutableStateFlow<CreditMovieState>(CreditMovieState.Loading)
    val creditMovieState: StateFlow<CreditMovieState> = _creditMovieState
    private val creditMovieUseCase: CreditsMoviesUseCase = AppModule.creditMoviesUseCase

    init {
        val filmIdString: String? = savedStateHandle["filmId"]
        val movieId = filmIdString?.toIntOrNull() ?: 0

        if (movieId > 0) {
            fetchMovieCredit(movieId)
        } else {
            _creditMovieState.value = CreditMovieState.Error("ID Film tidak valid")
        }
    }

    fun fetchMovieCredit(id: Int) {
        viewModelScope.launch {
            _creditMovieState.value = CreditMovieState.Loading
            try {
                val result = creditMovieUseCase(movieId = id)
                _creditMovieState.value = CreditMovieState.Success(result)
            } catch (e: Exception) {
                _creditMovieState.value = CreditMovieState.Error(e.message ?: "Unknown error")
            }
        }
    }

}