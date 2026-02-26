package com.example.flickpick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.flickpick.presentation.navigation.AppNavGraph
import com.example.flickpick.ui.theme.FlickpickTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main entry point for the FlickPick app.
 * Annotated with [AndroidEntryPoint] for Hilt injection support.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlickpickTheme {
                AppNavGraph()
            }
        }
    }
}