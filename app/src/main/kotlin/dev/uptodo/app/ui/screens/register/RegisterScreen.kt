package dev.uptodo.app.ui.screens.register

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.uptodo.app.R
import dev.uptodo.app.ui.components.AuthComponent
import dev.uptodo.app.ui.theme.UpTodoTheme
import dev.uptodo.app.util.handleCredentialRetrieval
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    uiState: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onNavigateToMainScreen: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    val error = uiState.error
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(error) {
        error?.let {
            onShowSnackbar(it.asString(context), null)
            onEvent(RegisterEvent.ClearError)
        }
    }

    AuthComponent(
        email = uiState.email,
        password = uiState.password,
        title = stringResource(id = R.string.register),
        isLoading = uiState.isLoading,
        isPasswordVisible = uiState.isPasswordVisible,
        isRepeatedPasswordVisible = uiState.isRepeatedPasswordVisible,
        onEmailUpdate = { onEvent(RegisterEvent.UpdateEmail(it)) },
        onPasswordUpdate = { onEvent(RegisterEvent.UpdatePassword(it)) },
        onAuth = {
            onEvent(
                RegisterEvent.TryToRegister(
                    onNavigateToMainScreen = onNavigateToMainScreen
                )
            )
        },
        authWithGoogleText = stringResource(id = R.string.sign_up_with_google),
        onAuthWithGoogle = { credential ->
            onEvent(
                RegisterEvent.RegisterWithGoogle(
                    credential = credential,
                    onNavigateToMainScreen = onNavigateToMainScreen
                )
            )
        },
        swapAuthScreenText = stringResource(id = R.string.login_account),
        onSwapAuthScreen = onNavigateToLogin,
        onClearEmail = { onEvent(RegisterEvent.ClearEmail) },
        onNavigateUp = onNavigateToLogin,
        repeatedPassword = uiState.repeatedPassword,
        onUpdateRepeatedPassword = { onEvent(RegisterEvent.UpdateRepeatedPassword(it)) },
        onUpdatePasswordVisibility = { onEvent(RegisterEvent.TogglePasswordVisibility) },
        onUpdateRepeatedPasswordVisibility = { onEvent(RegisterEvent.ToggleRepeatedPasswordVisibility) },
        topBarLeadingIcon = Icons.AutoMirrored.Default.ArrowBack,
        onShowError = {
            coroutineScope.launch {
                val retry: Boolean = onShowSnackbar(it, context.getString(R.string.retry))

                if (retry) {
                    handleCredentialRetrieval(
                        context = context,
                        authAction = { credential ->
                            onEvent(
                                RegisterEvent.RegisterWithGoogle(
                                    credential = credential,
                                    onNavigateToMainScreen = onNavigateToMainScreen
                                )
                            )
                        },
                        onShowError = { msg -> onShowSnackbar(msg, null) }
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun LoginScreenPreview() {
    UpTodoTheme {
        RegisterScreen(
            uiState = RegisterState(),
            onShowSnackbar = { _, _ -> true },
            onNavigateToMainScreen = {},
            onEvent = {},
            onNavigateToLogin = {}
        )
    }
}