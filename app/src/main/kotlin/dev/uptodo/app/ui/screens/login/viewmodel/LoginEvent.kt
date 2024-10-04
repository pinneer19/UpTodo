package dev.uptodo.app.ui.screens.login.viewmodel

import androidx.credentials.Credential

sealed class LoginEvent {

    data class UpdateEmail(val email: String) : LoginEvent()

    data class UpdatePassword(val password: String) : LoginEvent()

    data class NavigateToRegister(val onNavigateToRegisterScreen: () -> Unit) : LoginEvent()

    data class TryToLogin(val onNavigateToMainScreen: () -> Unit) : LoginEvent()

    data class LoginWithGoogle(
        val credential: Credential, val onNavigateToMainScreen: () -> Unit
    ) : LoginEvent()

    data class ForgotPassword(val onNavigateToPasswordResetScreen: () -> Unit) : LoginEvent()

    data object ClearEmail : LoginEvent()

    data object ClearError : LoginEvent()

    data object TogglePasswordVisibility : LoginEvent()
}