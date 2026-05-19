package com.example.filmkuapps.ui.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmkuapps.di.AppModule
import com.example.filmkuapps.domain.model.MovieSearch
import com.example.filmkuapps.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.flow

class SearchViewModel(
    private val searchUseCase: SearchMoviesUseCase = AppModule.searchMoviesUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")                // user input
    fun setQuery(q: String) { _query.value = q }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _results = MutableStateFlow<List<MovieSearch>>(emptyList())
    val results: StateFlow<List<MovieSearch>> = _results.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        viewModelScope.launch {
            _query
                .debounce(500L)                         // delay typing
                .map { it.trim() }
                .distinctUntilChanged()
                .flatMapLatest { q ->
                    if (q.isEmpty() || q.length < 2) {
                        // short queries -> emit empty list, no network call
                        flow { emit(emptyList<MovieSearch>()) }
                    } else {
                        flow {
                            _isLoading.value = true
                            _error.value = null
                            val response = searchUseCase(q) // suspend -> List<MovieSearch>
                            emit(response)
                        }.catch { e ->
                            _error.value = e.message ?: "Unknown error"
                            emit(emptyList())
                        }.onStart { /* already handled */ }
                    }
                }
                .collect { list ->
                    _isLoading.value = false
                    _results.value = list
                }
        }
    }
}