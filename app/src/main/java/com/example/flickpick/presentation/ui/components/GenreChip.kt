package com.example.flickpick.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A chip displaying a movie genre name.
 *
 * @param genre The genre label to display.
 */
@Composable
fun GenreChip(
    genre: String,
    modifier: Modifier = Modifier
) {
    SuggestionChip(
        onClick = { /* No action — display only */ },
        label = {
            Text(
                text = genre,
                style = MaterialTheme.typography.labelMedium
            )
        },
        modifier = modifier.padding(end = 4.dp),
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            labelColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    )
}
