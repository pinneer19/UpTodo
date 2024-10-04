package dev.uptodo.app.ui.screens.login.viewmodel

import dev.uptodo.app.ui.util.UiText

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: UiText? = null
)