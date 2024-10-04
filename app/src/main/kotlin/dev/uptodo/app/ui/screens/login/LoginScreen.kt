package dev.uptodo.app.ui.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import dev.uptodo.app.ui.components.TopBarComponent
import dev.uptodo.app.ui.screens.login.viewmodel.LoginEvent
import dev.uptodo.app.ui.screens.login.viewmodel.LoginState
import dev.uptodo.app.ui.theme.UpTodoTheme
import dev.uptodo.app.ui.util.SnackbarAction
import dev.uptodo.app.ui.util.SnackbarController
import dev.uptodo.app.ui.util.SnackbarEvent
import dev.uptodo.app.util.helper.handleCredentialRetrieval
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    uiState: LoginState,
    onEvent: (LoginEvent) -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToMainScreen: () -> Unit,
    onNavigateToPasswordResetScreen: () -> Unit,
) {
    val error = uiState.error
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(error) {
        error?.let {
            SnackbarController.sendMessageEvent(
                message = it.asString(context)
            )
            onEvent(LoginEvent.ClearError)
        }
    }

    Scaffold(
        topBar = {
            TopBarComponent(title = stringResource(id = R.string.login))
        }
    ) { contentPadding ->
        AuthComponent(
            title = stringResource(id = R.string.login),
            email = uiState.email,
            password = uiState.password,
            modifier = Modifier.padding(contentPadding),
            onEmailUpdate = { onEvent(LoginEvent.UpdateEmail(it)) },
            onPasswordUpdate = { onEvent(LoginEvent.UpdatePassword(it)) },
            onAuth = {
                onEvent(
                    LoginEvent.TryToLogin(
                        onNavigateToMainScreen = onNavigateToMainScreen
                    )
                )
            },
            authWithGoogleText = stringResource(id = R.string.continue_with_google),
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
                val snackbarAction: suspend () -> Unit = {
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
                        onShowError = { msg -> SnackbarController.sendMessageEvent(msg) }
                    )
                }

                coroutineScope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = it,
                            action = SnackbarAction(
                                name = context.getString(R.string.retry),
                                action = snackbarAction
                            )
                        )
                    )
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
}

@Preview
@Composable
private fun LoginScreenPreview() {
    UpTodoTheme {
        LoginScreen(
            uiState = LoginState(),
            onNavigateToMainScreen = {},
            onEvent = {},
            onNavigateToRegister = {},
            onNavigateToPasswordResetScreen = {}
        )
    }
}