package com.example.flickpick.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.flickpick.domain.model.Genre
import com.example.flickpick.domain.model.Movie
import com.example.flickpick.domain.repository.MovieRepository
import com.example.flickpick.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Home screen.
 * Manages paged popular, top-rated, and genre-filtered movie lists.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _selectedTabIndex = MutableStateFlow(0)
    /** Currently selected tab index (0 = Popular, 1 = Top Rated). */
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    /** Paged popular movies. */
    val popularMovies: Flow<PagingData<Movie>> =
        movieRepository.getPopularMovies().cachedIn(viewModelScope)

    /** Paged top-rated movies. */
    val topRatedMovies: Flow<PagingData<Movie>> =
        movieRepository.getTopRatedMovies().cachedIn(viewModelScope)

    // ── Genre filter ────────────────────────────────────────────

    private val _genres = MutableStateFlow<UiState<List<Genre>>>(UiState.Loading)
    /** State of the genre list. */
    val genres: StateFlow<UiState<List<Genre>>> = _genres.asStateFlow()

    private val _selectedGenreId = MutableStateFlow<Int?>(null)
    /** Currently selected genre ID for filtering (null = show popular/top-rated). */
    val selectedGenreId: StateFlow<Int?> = _selectedGenreId.asStateFlow()

    /** Paged movies for the selected genre. Emits a new pager each time genre changes. */
    @OptIn(ExperimentalCoroutinesApi::class)
    val genreMovies: Flow<PagingData<Movie>> = _selectedGenreId
        .flatMapLatest { genreId ->
            if (genreId != null) {
                movieRepository.discoverByGenre(genreId)
            } else {
                // When no genre is selected, return empty flow
                kotlinx.coroutines.flow.flowOf(PagingData.empty())
            }
        }.cachedIn(viewModelScope)

    init {
        fetchGenres()
    }

    /** Fetches available genres from the repository. */
    private fun fetchGenres() {
        viewModelScope.launch {
            movieRepository.getGenres().collect { state ->
                _genres.value = state
            }
        }
    }

    /** Updates the selected tab index. */
    fun selectTab(index: Int) {
        _selectedTabIndex.value = index
    }

    /** Selects a genre for filtering, or null to clear the filter. */
    fun selectGenre(genreId: Int?) {
        _selectedGenreId.value = genreId
    }
}
