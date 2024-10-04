package dev.uptodo.app.ui.screens.task.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Edit
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import dev.uptodo.app.R

@Composable
fun TaskInfo(
    name: String,
    description: String,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.Circle,
    editIcon: ImageVector = Icons.Outlined.Edit,
    onUpdateTaskName: (String) -> Unit,
    onUpdateTaskDescription: (String) -> Unit,
) {
    var isEditing by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isEditing) {
        if (isEditing) {
            focusRequester.requestFocus()
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 10.dp)
                .then(
                    if (isEditing) Modifier
                        .align(Alignment.Top)
                        .padding(top = 16.dp)
                    else Modifier
                )
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(space = 10.dp),
            modifier = Modifier
                .weight(1f)
                .animateContentSize(
                    animationSpec = spring(stiffness = Spring.StiffnessMedium)
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = { isEditing = !isEditing }
                    )
                }
        ) {
            if (isEditing) {
                TextField(
                    value = name,
                    onValueChange = onUpdateTaskName,
                    placeholder = {
                        Text(text = stringResource(id = R.string.name))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    )
                )

                TextField(
                    value = description,
                    onValueChange = onUpdateTaskDescription,
                    placeholder = {
                        Text(text = stringResource(id = R.string.description))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    keyboardActions = KeyboardActions(
                        onDone = { isEditing = false }
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    )
                )
            } else {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (description.isNotEmpty()) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.Top)
                .then(
                    if (isEditing) {
                        Modifier
                            .align(Alignment.Top)
                            .padding(top = 6.dp)
                    } else Modifier
                ),
            onClick = { isEditing = !isEditing },
        ) {
            Icon(imageVector = editIcon, contentDescription = null)
        }
    }
}