package dev.uptodo.app.ui.screens.passwordReset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.uptodo.app.R
import dev.uptodo.app.util.UiText
import dev.uptodo.app.util.toUiText
import dev.uptodo.domain.usecase.ResetPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class PasswordResetViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PasswordResetState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: PasswordResetEvent) {
        when (event) {
            is PasswordResetEvent.SendResetRequest -> sendPasswordResetEmail()

            is PasswordResetEvent.UpdateEmail -> updateEmail(event.email)

            PasswordResetEvent.ClearEmail -> updateEmail("")

            PasswordResetEvent.ClearMessage -> clearMessage()
        }
    }

    private fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    private fun sendPasswordResetEmail() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(loading = true) }
                resetPasswordUseCase(_uiState.value.email)
                updateMessage(message = R.string.reset_password_email.toUiText())
            } catch (e: Exception) {
                updateMessage(e.message.toUiText())
            } finally {
                _uiState.update { it.copy(loading = false) }
            }
        }
    }

    private fun updateMessage(message: UiText?) {
        _uiState.update { it.copy(message = message) }
    }

    private fun clearMessage() {
        _uiState.update { it.copy(message = null) }
    }
}