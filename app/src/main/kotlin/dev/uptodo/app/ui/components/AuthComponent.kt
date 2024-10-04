package dev.uptodo.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.credentials.Credential
import dev.uptodo.app.R

@Composable
fun AuthComponent(
    title: String,
    email: String,
    password: String,
    isLoading: Boolean,
    isPasswordVisible: Boolean,
    authWithGoogleText: String,
    swapAuthScreenText: String,
    onEmailUpdate: (String) -> Unit,
    onPasswordUpdate: (String) -> Unit,
    onAuth: () -> Unit,
    onAuthWithGoogle: (Credential) -> Unit,
    onSwapAuthScreen: () -> Unit,
    onClearEmail: () -> Unit,
    onUpdatePasswordVisibility: () -> Unit,
    onShowError: (String) -> Unit,
    modifier: Modifier = Modifier,
    repeatedPassword: String? = null,
    isRepeatedPasswordVisible: Boolean? = null,
    onUpdateRepeatedPassword: ((String) -> Unit)? = null,
    onUpdateRepeatedPasswordVisibility: (() -> Unit)? = null,
    onForgotPassword: (() -> Unit)? = null,
) {
    val focusManager = LocalFocusManager.current
    val repeatPassword = repeatedPassword != null

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .imePadding()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        TextFieldComponent(
            value = email,
            label = stringResource(id = R.string.email),
            placeholder = stringResource(id = R.string.email_placeholder),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(),
            onValueChange = onEmailUpdate,
            trailingIcon = { value ->
                if (value.isNotEmpty()) {
                    IconButton(onClick = onClearEmail) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                    }
                }
            },
        )

        TextFieldComponent(
            value = password,
            label = stringResource(id = R.string.password),
            placeholder = stringResource(id = R.string.password_placeholder),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = if (!repeatPassword) ImeAction.Done else ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onAuth()
                }
            ),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            onValueChange = onPasswordUpdate,
            trailingIcon = { _ ->
                IconButton(onClick = onUpdatePasswordVisibility) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            }
        )

        if (repeatPassword) {
            TextFieldComponent(
                modifier = Modifier.padding(top = 25.dp, bottom = 69.dp),
                value = requireNotNull(repeatedPassword),
                label = stringResource(id = R.string.repeat_password),
                placeholder = stringResource(id = R.string.repeat_password),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onAuth()
                    }
                ),
                visualTransformation =
                if (isRepeatedPasswordVisible == true) VisualTransformation.None
                else PasswordVisualTransformation(),
                onValueChange = requireNotNull(onUpdateRepeatedPassword),
                trailingIcon = {
                    IconButton(onClick = requireNotNull(onUpdateRepeatedPasswordVisibility)) {
                        Icon(
                            imageVector =
                            if (isRepeatedPasswordVisible == true) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                }
            )
        } else {
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.End)
                    .padding(top = 8.dp, bottom = 69.dp),
                onClick = requireNotNull(onForgotPassword)
            ) {
                Text(text = stringResource(id = R.string.forgot_password))
            }
        }

        Button(
            onClick = {
                focusManager.clearFocus()
                onAuth()
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(Modifier.weight(1f))

            Text(stringResource(id = R.string.or), Modifier.padding(horizontal = 10.dp))

            HorizontalDivider(Modifier.weight(1f))
        }

        AuthenticationButton(
            text = authWithGoogleText,
            authAction = {
                onAuthWithGoogle(it)
            },
            onShowError = onShowError,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp),
        )

        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = onSwapAuthScreen) {
            Text(swapAuthScreenText)
        }
    }

    AnimatedVisibility(
        visible = isLoading,
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