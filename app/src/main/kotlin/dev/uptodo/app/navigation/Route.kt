package dev.uptodo.app.navigation

import dev.uptodo.domain.model.Task
import kotlinx.serialization.Serializable

private annotation class Graph
private annotation class Auth
private annotation class Main

sealed class Route {

    @Serializable
    data object Splash : Route()

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
    data object Category : Route()

    @Main
    @Serializable
    data class TaskDetails(val id: String, val task: Task) : Route()
}

fun String.showBottomBar(): Boolean {
    return this !in listOf(
        Route.Splash::class.qualifiedName.toString(),
        Route.Login::class.qualifiedName.toString(),
        Route.Register::class.qualifiedName.toString(),
        Route.PasswordReset::class.qualifiedName.toString(),
        Route.Category::class.qualifiedName.toString(),
        Route.TaskDetails::class.qualifiedName.toString()
    )
}