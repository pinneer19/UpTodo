package dev.uptodo.app.ui.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.uptodo.app.R
import dev.uptodo.app.ui.components.AuthComponent
import dev.uptodo.app.ui.theme.UpTodoTheme
import dev.uptodo.app.util.handleCredentialRetrieval
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    uiState: LoginState,
    onEvent: (LoginEvent) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onNavigateToRegister: () -> Unit,
    onNavigateToMainScreen: () -> Unit,
    onNavigateToPasswordResetScreen: () -> Unit,
) {
    val error = uiState.error
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(error) {
        error?.let {
            onShowSnackbar(it.asString(context), null)
            onEvent(LoginEvent.ClearError)
        }
    }

    AuthComponent(
        title = stringResource(id = R.string.login),
        email = uiState.email,
        password = uiState.password,
        onEmailUpdate = { onEvent(LoginEvent.UpdateEmail(it)) },
        onPasswordUpdate = { onEvent(LoginEvent.UpdatePassword(it)) },
        onAuth = {
            onEvent(
                LoginEvent.TryToLogin(
                    onNavigateToMainScreen = onNavigateToMainScreen
                )
            )
        },
        authWithGoogleText = stringResource(id = R.string.sign_in_with_google),
        onAuthWithGoogle = { credential ->
            onEvent(
                LoginEvent.LoginWithGoogle(
                    credential = credential,
                    onNavigateToMainScreen = onNavigateToMainScreen
                )
            )
        },
        swapAuthScreenText = stringResource(id = R.string.register_account),
        onSwapAuthScreen = onNavigateToRegister,
        onUpdatePasswordVisibility = { onEvent(LoginEvent.TogglePasswordVisibility) },
        onClearEmail = { onEvent(LoginEvent.ClearEmail) },
        onForgotPassword = {
            onEvent(
                LoginEvent.ForgotPassword(
                    onNavigateToPasswordResetScreen = onNavigateToPasswordResetScreen
                )
            )
        },
        isLoading = uiState.isLoading,
        isPasswordVisible = uiState.isPasswordVisible,
        onShowError = {
            coroutineScope.launch {
                val retry: Boolean = onShowSnackbar(it, context.getString(R.string.retry))

                if (retry) {
                    handleCredentialRetrieval(
                        context = context,
                        authAction = { credential ->
                            onEvent(
                                LoginEvent.LoginWithGoogle(
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

    AnimatedVisibility(
        visible = uiState.isLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    UpTodoTheme {
        LoginScreen(
            uiState = LoginState(),
            onShowSnackbar = { _, _ -> true },
            onNavigateToMainScreen = {},
            onEvent = {},
            onNavigateToRegister = {},
            onNavigateToPasswordResetScreen = {}
        )
    }
}