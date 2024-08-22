package com.dejvidleka.moviehub_jetpackcompose.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItems(
    val icon: ImageVector,
    val string: String,
    val route: Destination
) {
    HOME(Icons.Default.Home, "Home", Destination.Home),
    SEARCH(Icons.Default.Search, "Home", Destination.Search),
    FAVORITES(Icons.Default.Favorite, "Home", Destination.Favorites),
    SETTINGS(Icons.Default.Settings, "Home", Destination.Settings),
}