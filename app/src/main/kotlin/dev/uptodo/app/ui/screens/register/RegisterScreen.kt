package dev.uptodo.app.ui.screens.register

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.uptodo.app.R
import dev.uptodo.app.ui.components.AuthComponent
import dev.uptodo.app.ui.components.TopBarComponent
import dev.uptodo.app.ui.screens.register.viewmodel.RegisterEvent
import dev.uptodo.app.ui.screens.register.viewmodel.RegisterState
import dev.uptodo.app.ui.theme.UpTodoTheme
import dev.uptodo.app.ui.util.SnackbarAction
import dev.uptodo.app.ui.util.SnackbarController
import dev.uptodo.app.ui.util.SnackbarEvent
import dev.uptodo.app.util.helper.handleCredentialRetrieval
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    uiState: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    onNavigateToMainScreen: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    val error = uiState.error
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(error) {
        error?.let {
            SnackbarController.sendMessageEvent(
                message = it.asString(context)
            )
            onEvent(RegisterEvent.ClearError)
        }
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = stringResource(id = R.string.register),
                leadingIcon = Icons.AutoMirrored.Default.ArrowBack,
                leadingAction = onNavigateToLogin
            )
        }
    ) { contentPadding ->
        AuthComponent(
            email = uiState.email,
            password = uiState.password,
            title = stringResource(id = R.string.register),
            isLoading = uiState.isLoading,
            isPasswordVisible = uiState.isPasswordVisible,
            isRepeatedPasswordVisible = uiState.isRepeatedPasswordVisible,
            modifier = Modifier.padding(contentPadding),
            onEmailUpdate = { onEvent(RegisterEvent.UpdateEmail(it)) },
            onPasswordUpdate = { onEvent(RegisterEvent.UpdatePassword(it)) },
            onAuth = {
                onEvent(
                    RegisterEvent.TryToRegister(
                        onNavigateToMainScreen = onNavigateToMainScreen
                    )
                )
            },
            authWithGoogleText = stringResource(id = R.string.continue_with_google),
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
            repeatedPassword = uiState.repeatedPassword,
            onUpdateRepeatedPassword = { onEvent(RegisterEvent.UpdateRepeatedPassword(it)) },
            onUpdatePasswordVisibility = { onEvent(RegisterEvent.TogglePasswordVisibility) },
            onUpdateRepeatedPasswordVisibility = { onEvent(RegisterEvent.ToggleRepeatedPasswordVisibility) },
            onShowError = {
                coroutineScope.launch {
                    val snackbarAction: suspend () -> Unit = {
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
                            onShowError = { msg -> SnackbarController.sendMessageEvent(message = msg) }
                        )
                    }

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
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    UpTodoTheme {
        RegisterScreen(
            uiState = RegisterState(),
            onNavigateToMainScreen = {},
            onEvent = {},
            onNavigateToLogin = {}
        )
    }
}