package com.example.flickpick.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickpick.domain.model.Movie
import com.example.flickpick.domain.repository.MovieRepository
import com.example.flickpick.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Search screen.
 * Debounces user input and triggers search queries against the repository.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    /** Current search query text. */
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<UiState<List<Movie>>>(UiState.Success(emptyList()))
    /** State of the search results. */
    val searchResults: StateFlow<UiState<List<Movie>>> = _searchResults.asStateFlow()

    private var searchJob: Job? = null

    /** Updates the search query and triggers a debounced search. */
    fun onQueryChanged(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()

        if (query.isBlank()) {
            _searchResults.value = UiState.Success(emptyList())
            return
        }

        searchJob = viewModelScope.launch {
            delay(300L) // Debounce
            movieRepository.searchMovies(query).collect { state ->
                _searchResults.value = state
            }
        }
    }
}
