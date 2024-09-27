package com.dejvidleka.moviehub_jetpackcompose.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItems(
    val icon: ImageVector,
    val string: String,
    val route: Destination
) {
    HOME(Icons.Default.Home, "Home", Destination.Home),
    SEARCH(Icons.Default.Search, "Search", Destination.Search),
    FAVORITES(Icons.Default.Menu, "Forum", Destination.News),
    SETTINGS(Icons.Default.Person, "Profile", Destination.Profile),
}