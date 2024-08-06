package dev.uptodo.app.ui.screens.register

import androidx.credentials.Credential

sealed class RegisterEvent {

    data class UpdateEmail(val email: String) : RegisterEvent()

    data class UpdatePassword(val password: String) : RegisterEvent()

    data class UpdateRepeatedPassword(val repeatedPassword: String) : RegisterEvent()

    data class NavigateToLogin(val onNavigateToLoginScreen: () -> Unit) : RegisterEvent()

    data class TryToRegister(val onNavigateToMainScreen: () -> Unit) : RegisterEvent()

    data class RegisterWithGoogle(
        val credential: Credential, val onNavigateToMainScreen: () -> Unit
    ) : RegisterEvent()

    data object TogglePasswordVisibility : RegisterEvent()

    data object ToggleRepeatedPasswordVisibility : RegisterEvent()

    data object ClearEmail : RegisterEvent()

    data object ClearError : RegisterEvent()
}