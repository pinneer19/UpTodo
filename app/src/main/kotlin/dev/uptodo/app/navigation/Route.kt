package dev.uptodo.app.navigation

import kotlinx.serialization.Serializable

sealed class Route {

    @Serializable
    data object Splash : Route()

    @Serializable
    data object AuthGraph : Route()

    @Serializable
    data object MainGraph : Route()
}