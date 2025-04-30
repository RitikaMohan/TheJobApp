package com.example.thejobapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Bookmarks : Screen("bookmarks", "Bookmarks", Icons.Default.Favorite)

    object JobDetails : Screen("job_details/{jobId}", "Details", Icons.Default.Info) {
        fun passJobId(jobId: String) = "job_details/$jobId"
    }
}