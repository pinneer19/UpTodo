package dev.uptodo.app.ui.screens.passwordReset

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.uptodo.app.R
import dev.uptodo.app.ui.components.TextFieldComponent
import dev.uptodo.app.ui.components.TopBarComponent
import dev.uptodo.ui.theme.UpTodoTheme

@Composable
fun PasswordResetScreen(
    uiState: PasswordResetState,
    onEvent: (PasswordResetEvent) -> Unit,
    onNavigateUp: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarComponent(
            title = stringResource(id = R.string.reset_password),
            leadingIcon = Icons.AutoMirrored.Default.ArrowBack,
            leadingAction = onNavigateUp
        )

        Spacer(modifier = Modifier.weight(1f))

        TextFieldComponent(
            value = uiState.email,
            label = stringResource(id = R.string.email),
            placeholder = stringResource(R.string.enter_reset_password_email),
            onValueChange = { onEvent(PasswordResetEvent.UpdateEmail(it)) },
            trailingIcon = {
                if (uiState.email.isNotEmpty()) {
                    IconButton(onClick = { onEvent(PasswordResetEvent.ClearEmail) }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                    }
                }
            }
        )

        Button(
            onClick = { onEvent(PasswordResetEvent.SendResetRequest) },
            modifier = Modifier
                .padding(vertical = 20.dp)
                .width(125.dp)
        ) {
            Text(text = stringResource(R.string.reset))
        }

        AnimatedVisibility(
            visible = uiState.message != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.LightGray)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,

            ) {
                Text(
                    modifier = Modifier
                        .padding(20.dp)
                        .weight(1f),
                    textAlign = TextAlign.Justify,
                    text = uiState.message?.asString().orEmpty(),
                    style = MaterialTheme.typography.bodyMedium
                )

                IconButton(onClick = { onEvent(PasswordResetEvent.ClearMessage) }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}


@Preview(showBackground = true)
@Composable
private fun PasswordResetScreenPreview() {
    UpTodoTheme {
        PasswordResetScreen(
            uiState = PasswordResetState(),
            onEvent = {},
            onNavigateUp = {}
        )
    }
}