package dev.uptodo.app.ui.screens.passwordReset

sealed class PasswordResetEvent {

    data object SendResetRequest: PasswordResetEvent()

    data class UpdateEmail(val email: String): PasswordResetEvent()

    data object ClearEmail: PasswordResetEvent()

    data object ClearMessage: PasswordResetEvent()
}