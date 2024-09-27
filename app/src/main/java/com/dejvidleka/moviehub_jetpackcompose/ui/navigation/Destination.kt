package com.dejvidleka.moviehub_jetpackcompose.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destination {
    @Serializable
    data object Home : Destination()
    @Serializable
    data object Search : Destination()
    @Serializable
    data object News : Destination()
    @Serializable
    data object Profile : Destination()

}