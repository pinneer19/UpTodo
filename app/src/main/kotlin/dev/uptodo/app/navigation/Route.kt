package dev.uptodo.app.navigation

import kotlinx.serialization.Serializable

private annotation class Graph
private annotation class Auth
private annotation class Main

sealed class Route {

    @Graph
    @Serializable
    data object AuthGraph : Route()

    @Auth
    @Serializable
    data object Login : Route()

    @Auth
    @Serializable
    data object Register : Route()

    @Auth
    @Serializable
    data object PasswordReset : Route()

    @Graph
    @Serializable
    data object MainGraph : Route()

    @Main
    @Serializable
    data object Home : Route()

    @Main
    @Serializable
    data object AddTaskCategory : Route()
}

fun String.isAuthRoute(): Boolean = this in listOf(
    Route.Login::class.qualifiedName.toString(),
    Route.Register::class.qualifiedName.toString(),
    Route.PasswordReset::class.qualifiedName.toString()
)