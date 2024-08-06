package dev.uptodo.app.ui.screens.register

import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import dev.uptodo.app.R
import dev.uptodo.app.util.toUiText
import dev.uptodo.domain.model.RegisterInputValidationType
import dev.uptodo.domain.usecase.RegisterWithEmailUseCase
import dev.uptodo.domain.usecase.RegisterWithGoogleUseCase
import dev.uptodo.domain.usecase.ValidateRegisterInputUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val validateRegisterInputUseCase: ValidateRegisterInputUseCase,
    private val registerWithEmailAndPasswordUseCase: RegisterWithEmailUseCase,
    private val registerWithGoogleUseCase: RegisterWithGoogleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.UpdateEmail -> updateEmail(event.email)

            is RegisterEvent.UpdatePassword -> updatePassword(event.password)

            is RegisterEvent.UpdateRepeatedPassword -> updateRepeatedPassword(event.repeatedPassword)

            is RegisterEvent.NavigateToLogin -> event.onNavigateToLoginScreen()

            is RegisterEvent.TryToRegister -> tryToRegister(event.onNavigateToMainScreen)

            is RegisterEvent.RegisterWithGoogle -> registerWithGoogle(
                successNavigate = event.onNavigateToMainScreen,
                credential = event.credential
            )

            RegisterEvent.ClearEmail -> updateEmail("")

            RegisterEvent.ClearError -> updateErrorState(null)

            RegisterEvent.TogglePasswordVisibility -> togglePasswordVisibility()

            RegisterEvent.ToggleRepeatedPasswordVisibility -> toggleRepeatedPasswordVisibility()
        }
    }

    private fun updateEmail(email: String) = _uiState.update {
        it.copy(email = email)
    }

    private fun updatePassword(password: String) = _uiState.update {
        it.copy(password = password)
    }

    private fun updateRepeatedPassword(repeatedPassword: String) = _uiState.update {
        it.copy(repeatedPassword = repeatedPassword)
    }

    private fun tryToRegister(navigateToMainScreen: () -> Unit) {

        val email = _uiState.value.email
        val password = _uiState.value.password
        val repeatedPassword = _uiState.value.repeatedPassword

        val validateStatus =
            validateRegisterInputUseCase(email, password, repeatedPassword)

        when (validateStatus) {
            RegisterInputValidationType.Valid -> {
                register(
                    registerAction = { registerWithEmailAndPasswordUseCase(email, password) },
                    successNavigate = navigateToMainScreen
                )
            }

            RegisterInputValidationType.EmptyField -> updateErrorState(R.string.empty_field)

            RegisterInputValidationType.IncorrectEmail -> updateErrorState(R.string.check_email_format)

            RegisterInputValidationType.PasswordsAreNotTheSame -> updateErrorState(R.string.passwords_are_not_the_same)

            RegisterInputValidationType.TooShortPassword -> updateErrorState(R.string.too_short_password)

            RegisterInputValidationType.PasswordLowerCaseMissing -> updateErrorState(R.string.lower_case_missing)

            RegisterInputValidationType.PasswordUpperCaseMissing -> updateErrorState(R.string.password_upper_case_missing)

            RegisterInputValidationType.PasswordSpecialCharacterMissing -> updateErrorState(R.string.special_character_missing)

            RegisterInputValidationType.PasswordNumeralsMissing -> updateErrorState(R.string.numerals_missing)
        }
    }

    private fun updateErrorState(errorResId: Int) {
        _uiState.update { it.copy(error = errorResId.toUiText()) }
    }

    private fun updateErrorState(error: String?) {
        _uiState.update { it.copy(error = error.toUiText()) }
    }

    private fun registerWithGoogle(successNavigate: () -> Unit, credential: Credential) {
        register(
            successNavigate = successNavigate,
            registerAction = {
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)

                    registerWithGoogleUseCase(googleIdTokenCredential.idToken)
                } else {
                    updateErrorState(R.string.unexpected_credential)
                }
            }
        )
    }

    private fun register(registerAction: suspend () -> Unit, successNavigate: () -> Unit) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                registerAction()
                successNavigate()
            } catch (e: FirebaseAuthException) {
                updateErrorState(e.message)
            } catch (e: FirebaseTooManyRequestsException) {
                updateErrorState(R.string.too_many_requests)
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    private fun toggleRepeatedPasswordVisibility() {
        _uiState.update { it.copy(isRepeatedPasswordVisible = !it.isRepeatedPasswordVisible) }
    }
}