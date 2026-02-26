package com.example.flickpick.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickpick.domain.model.MovieDetail
import com.example.flickpick.domain.repository.MovieRepository
import com.example.flickpick.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Detail screen.
 * Receives [movieId] via [SavedStateHandle] and fetches full movie details.
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val movieId: Int = savedStateHandle.get<Int>("movieId") ?: -1

    private val _movieDetail = MutableStateFlow<UiState<MovieDetail>>(UiState.Loading)
    /** State of the movie detail data. */
    val movieDetail: StateFlow<UiState<MovieDetail>> = _movieDetail.asStateFlow()

    init {
        fetchMovieDetail()
    }

    /** Fetches movie details from the repository. */
    fun fetchMovieDetail() {
        viewModelScope.launch {
            movieRepository.getMovieDetail(movieId).collect { state ->
                _movieDetail.value = state
            }
        }
    }
}
