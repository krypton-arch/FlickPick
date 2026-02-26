package com.example.flickpick.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flickpick.domain.model.Movie
import com.example.flickpick.presentation.ui.components.ErrorContent
import com.example.flickpick.presentation.ui.components.LoadingContent
import com.example.flickpick.presentation.ui.components.MovieCard
import com.example.flickpick.presentation.viewmodel.HomeViewModel
import com.example.flickpick.util.UiState

/**
 * Home screen displaying Popular and Top Rated movie tabs in a grid.
 *
 * @param onMovieClick Callback when a movie card is tapped, receiving the movie ID.
 * @param viewModel Injected [HomeViewModel] instance.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val selectedTab by viewModel.selectedTabIndex.collectAsStateWithLifecycle()
    val popularState by viewModel.popularMovies.collectAsStateWithLifecycle()
    val topRatedState by viewModel.topRatedMovies.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "FlickPick 🎬",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val tabs = listOf("Popular", "Top Rated")
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { viewModel.selectTab(index) },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    )
                }
            }

            val currentState = if (selectedTab == 0) popularState else topRatedState

            when (currentState) {
                is UiState.Loading -> LoadingContent()
                is UiState.Error -> ErrorContent(
                    message = (currentState as UiState.Error).message,
                    onRetry = {
                        if (selectedTab == 0) viewModel.fetchPopularMovies()
                        else viewModel.fetchTopRatedMovies()
                    }
                )
                is UiState.Success -> {
                    val movies = (currentState as UiState.Success<List<Movie>>).data
                    MovieGrid(
                        movies = movies,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}

/**
 * A 2-column grid of [MovieCard] items.
 */
@Composable
private fun MovieGrid(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = movies,
            key = { it.id }
        ) { movie ->
            MovieCard(
                movie = movie,
                onClick = { onMovieClick(movie.id) }
            )
        }
    }
}
