package dev.uptodo.app.ui.screens.passwordReset.viewmodel

import dev.uptodo.app.ui.util.UiText

data class PasswordResetState(
    val email: String = "",
    val loading: Boolean = false,
    val message: UiText? = null
)