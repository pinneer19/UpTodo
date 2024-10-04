package dev.uptodo.app.navigation

import kotlinx.serialization.Serializable

sealed class AuthRoute : Route() {

    @Serializable
    data object Login : AuthRoute()

    @Serializable
    data object Register : AuthRoute()

    @Serializable
    data object PasswordReset : AuthRoute()
}