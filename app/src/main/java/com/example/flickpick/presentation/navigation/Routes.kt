package com.example.flickpick.presentation.navigation

/**
 * Navigation route constants for the app.
 */
object Routes {
    const val HOME = "home"
    const val SEARCH = "search"
    const val FAVOURITES = "favourites"
    const val DETAIL = "detail/{movieId}"

    /** Builds a detail route for the given [movieId]. */
    fun detail(movieId: Int) = "detail/$movieId"
}
