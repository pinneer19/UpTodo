package dev.uptodo.app.ui.screens.passwordReset.viewmodel

sealed class PasswordResetEvent {

    data class UpdateEmail(val email: String): PasswordResetEvent()

    data object SendResetRequest: PasswordResetEvent()

    data object ClearEmail: PasswordResetEvent()

    data object ClearMessage: PasswordResetEvent()
}