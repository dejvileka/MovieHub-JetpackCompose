package com.dejvidleka.moviehub_jetpackcompose.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dejvidleka.moviehub_jetpackcompose.ui.navigation.BottomNavItems
import com.dejvidleka.moviehub_jetpackcompose.ui.navigation.Destination
import com.dejvidleka.moviehub_jetpackcompose.ui.screens.FavoritesScreen
import com.dejvidleka.moviehub_jetpackcompose.ui.screens.HomeScreen
import com.dejvidleka.moviehub_jetpackcompose.ui.screens.SearchScreen
import com.dejvidleka.moviehub_jetpackcompose.ui.screens.SettingsScreen


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Navigation(navController = navController)
        }
    }

}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destination.Home) {
        composable<Destination.Home> {
            HomeScreen(
            )
        }
        composable<Destination.Search> {
            SearchScreen(
            )
        }
        composable<Destination.News> {
            FavoritesScreen(
            )
        }
        composable<Destination.Profile> {
            SettingsScreen(
            )
        }
}
}
@Composable
fun BottomNavigationBar(navController: NavController) {
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavItems.entries.forEachIndexed { index, item ->
        if (item.route::class.qualifiedName == currentDestination?.route) {
            selectedIndex.intValue = index
        }
        NavigationBar(containerColor = Color.Transparent, tonalElevation = 0.dp) {
            BottomNavItems.entries.forEachIndexed { index, item ->
                val isSelected = index == selectedIndex.intValue
                NavigationBarItem(
                    selected = isSelected,
                    label = {
                        Text(
                            item.string,
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon, contentDescription = ""
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1F)
                    ),
                    alwaysShowLabel = true,
                    onClick = {
                        if (index == selectedIndex.intValue) {
                            navController.popBackStack(item.route, inclusive = false)
                        } else {
                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        selectedIndex.intValue = index
                    }
                )
            }
        }
    }
}