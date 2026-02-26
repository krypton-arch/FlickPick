package com.example.flickpick.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickpick.domain.model.Movie
import com.example.flickpick.domain.repository.MovieRepository
import com.example.flickpick.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Home screen.
 * Manages popular and top-rated movie lists with tab selection state.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _popularMovies = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    /** State of popular movies list. */
    val popularMovies: StateFlow<UiState<List<Movie>>> = _popularMovies.asStateFlow()

    private val _topRatedMovies = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    /** State of top-rated movies list. */
    val topRatedMovies: StateFlow<UiState<List<Movie>>> = _topRatedMovies.asStateFlow()

    private val _selectedTabIndex = MutableStateFlow(0)
    /** Currently selected tab index (0 = Popular, 1 = Top Rated). */
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    // TODO: filter by genreId

    init {
        fetchPopularMovies()
        fetchTopRatedMovies()
    }

    /** Fetches popular movies from the repository. */
    fun fetchPopularMovies() {
        viewModelScope.launch {
            movieRepository.getPopularMovies().collect { state ->
                _popularMovies.value = state
            }
        }
    }

    /** Fetches top-rated movies from the repository. */
    fun fetchTopRatedMovies() {
        viewModelScope.launch {
            movieRepository.getTopRatedMovies().collect { state ->
                _topRatedMovies.value = state
            }
        }
    }

    /** Updates the selected tab index. */
    fun selectTab(index: Int) {
        _selectedTabIndex.value = index
    }
}
