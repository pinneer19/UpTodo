package dev.uptodo.app.ui.screens.passwordReset

import dev.uptodo.app.util.UiText

data class PasswordResetState(
    val email: String = "",
    val loading: Boolean = false,
    val message: UiText? = null
)