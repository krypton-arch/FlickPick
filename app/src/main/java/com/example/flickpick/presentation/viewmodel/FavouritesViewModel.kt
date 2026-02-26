package com.example.flickpick.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickpick.domain.model.Movie
import com.example.flickpick.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for the Favourites screen.
 * Observes the list of favourite movies from Room.
 */
@HiltViewModel
class FavouritesViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    /** Live list of favourite movies, newest first. */
    val favourites: StateFlow<List<Movie>> = movieRepository.getFavourites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
