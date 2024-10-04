package dev.uptodo.app.ui.screens.home.bottomsheet

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.uptodo.app.R
import dev.uptodo.app.ui.util.UiText

@Composable
fun TaskSheetContent(
    title: String,
    description: String,
    speaking: Boolean,
    error: UiText?,
    showDatePicker: () -> Unit,
    showCategoryPicker: () -> Unit,
    showPriorityPicker: () -> Unit,
    onUpdateTitle: (String) -> Unit,
    onUpdateDescription: (String) -> Unit,
    onCreateTask: () -> Unit,
    onRecordVoice: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    val infiniteTransition = rememberInfiniteTransition(label = "Infinite text transition")

    val iconScale by infiniteTransition.animateFloat(
        initialValue = 0.75f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Infinite mic animation"
    )

    var canRecord by remember {
        mutableStateOf(false)
    }

    val recordAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        canRecord = isGranted
    }

    LaunchedEffect(key1 = recordAudioLauncher) {
        recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.add_task),
            style = MaterialTheme.typography.headlineSmall
        )

        TextField(
            value = title,
            isError = error != null,
            label = error?.let {
                {
                    Text(text = it.asString())
                }
            },
            onValueChange = onUpdateTitle,
            placeholder = {
                Text(text = stringResource(R.string.title))
            },
            modifier = Modifier.padding(vertical = 13.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine = true,
        )

        TextField(
            value = description,
            onValueChange = onUpdateDescription,
            placeholder = {
                Text(text = stringResource(R.string.description))
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            singleLine = true,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 17.dp)
        ) {
            IconButton(onClick = showDatePicker) {
                Icon(
                    imageVector = Icons.Outlined.Timer,
                    contentDescription = stringResource(id = R.string.choose_task_time)
                )
            }

            IconButton(onClick = showCategoryPicker) {
                Icon(
                    imageVector = Icons.Outlined.Category,
                    contentDescription = stringResource(id = R.string.choose_task_category)
                )
            }

            IconButton(onClick = showPriorityPicker) {
                Icon(
                    imageVector = Icons.Outlined.Flag,
                    contentDescription = stringResource(id = R.string.choose_task_priority)
                )
            }

            IconButton(
                onClick = onRecordVoice,
                modifier = Modifier.then(
                    if (speaking) Modifier.graphicsLayer {
                        scaleX = iconScale
                        scaleY = iconScale
                    } else Modifier
                ),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Mic,
                    contentDescription = stringResource(id = R.string.record_task_title),
                    tint = if (speaking) Color.Red else MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = onCreateTask
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Send,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SheetMainContentPreview() {
    TaskSheetContent(
        title = "Title",
        description = "Description",
        showDatePicker = {},
        showCategoryPicker = {},
        showPriorityPicker = {},
        onUpdateTitle = {},
        onUpdateDescription = {},
        onCreateTask = {},
        onRecordVoice = {},
        error = null,
        speaking = true
    )
}