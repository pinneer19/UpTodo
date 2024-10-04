package dev.uptodo.app.ui.screens.login.viewmodel

import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import dev.uptodo.app.R
import dev.uptodo.app.di.auth.login.LoginScope
import dev.uptodo.app.ui.util.UiText
import dev.uptodo.app.ui.util.toUiText
import dev.uptodo.domain.model.LoginInputValidationType
import dev.uptodo.domain.usecase.LoginWithEmailUseCase
import dev.uptodo.domain.usecase.LoginWithGoogleUseCase
import dev.uptodo.domain.usecase.ValidateLoginInputUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@LoginScope
class LoginViewModel @Inject constructor(
    private val validateLoginInputUseCase: ValidateLoginInputUseCase,
    private val loginWithEmailAndPasswordUseCase: LoginWithEmailUseCase,
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UpdateEmail -> updateEmail(event.email)

            is LoginEvent.UpdatePassword -> updatePassword(event.password)

            is LoginEvent.NavigateToRegister -> navigate(event.onNavigateToRegisterScreen)

            is LoginEvent.TryToLogin -> tryToLogin(event.onNavigateToMainScreen)

            is LoginEvent.LoginWithGoogle -> login(
                successNavigate = event.onNavigateToMainScreen,
                loginAction = {
                    if (event.credential is CustomCredential && event.credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(event.credential.data)

                        loginWithGoogleUseCase(googleIdTokenCredential.idToken)
                    } else {
                        updateErrorState(error = R.string.unexpected_credential.toUiText())
                    }
                }
            )

            is LoginEvent.ForgotPassword -> navigate(event.onNavigateToPasswordResetScreen)

            LoginEvent.ClearEmail -> updateEmail("")

            LoginEvent.ClearError -> updateErrorState(null)

            LoginEvent.TogglePasswordVisibility -> togglePasswordVisibility()
        }
    }

    private fun updateEmail(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }

    private fun updatePassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    private fun navigate(navigateToScreen: () -> Unit) = navigateToScreen()

    private fun tryToLogin(navigateToMainScreen: () -> Unit) {
        val email = _uiState.value.email
        val password = _uiState.value.password

        val validateStatus =
            validateLoginInputUseCase(email, password)

        when (validateStatus) {
            LoginInputValidationType.Valid -> {
                login(
                    loginAction = { loginWithEmailAndPasswordUseCase(email, password) },
                    successNavigate = navigateToMainScreen
                )
            }

            LoginInputValidationType.EmptyField -> updateErrorState(error = R.string.empty_field.toUiText())

            LoginInputValidationType.IncorrectEmail -> updateErrorState(error = R.string.incorrect_email.toUiText())
        }
    }

    private fun login(
        loginAction: suspend () -> Unit,
        successNavigate: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                loginAction()
                successNavigate()
            } catch (e: FirebaseAuthException) {
                updateErrorState(error = e.message.toUiText())
            } catch (e: FirebaseTooManyRequestsException) {
                updateErrorState(error = R.string.too_many_requests.toUiText())
            } finally {
                _uiState.update { it.copy(isLoading = false) }

            }
        }
    }

    private fun updateErrorState(error: UiText?) {
        _uiState.update { it.copy(error = error) }
    }

    private fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }
}