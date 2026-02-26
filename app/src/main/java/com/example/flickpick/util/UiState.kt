package com.example.flickpick.util

/**
 * Sealed class representing the state of a UI operation.
 * Used by ViewModels to communicate loading, success, and error states to the UI.
 */
sealed class UiState<out T> {
    /** Indicates that data is currently being loaded. */
    object Loading : UiState<Nothing>()

    /** Indicates a successful operation with the resulting [data]. */
    data class Success<T>(val data: T) : UiState<T>()

    /** Indicates an error occurred with a descriptive [message]. */
    data class Error(val message: String) : UiState<Nothing>()
}
