package com.example.flickpick.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Outlined genre chip with translucent fill for a clean, modern look.
 */
@Composable
fun GenreChip(
    genre: String,
    modifier: Modifier = Modifier
) {
    SuggestionChip(
        onClick = { /* Display only */ },
        label = {
            Text(
                text = genre,
                style = MaterialTheme.typography.labelMedium
            )
        },
        modifier = modifier.padding(end = 4.dp),
        shape = RoundedCornerShape(20.dp),
        border = SuggestionChipDefaults.suggestionChipBorder(
            enabled = true,
            borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            borderWidth = 1.dp,
        ),
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}
