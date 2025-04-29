package com.example.thejobapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Jobs", Icons.Filled.Home)
    object Bookmarks : Screen("bookmarks", "Bookmarks", Icons.Filled.Favorite)
}