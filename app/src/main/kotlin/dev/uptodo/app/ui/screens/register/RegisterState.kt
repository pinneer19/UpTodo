package dev.uptodo.app.ui.screens.register

import dev.uptodo.app.util.UiText

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val repeatedPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isRepeatedPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: UiText? = null
)