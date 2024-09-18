package dev.uptodo.app.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.uptodo.app.R

@Composable
fun SheetMainContent(
    title: String,
    description: String,
    showDatePicker: () -> Unit,
    showCategoryPicker: () -> Unit,
    showPriorityPicker: () -> Unit,
    onUpdateSheetTaskTitle: (String) -> Unit,
    onUpdateSheetTaskDescription: (String) -> Unit,
    onCreateTask: () -> Unit
) {
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
            onValueChange = onUpdateSheetTaskTitle,
            placeholder = { Text(text = stringResource(R.string.title)) },
            modifier = Modifier.padding(vertical = 13.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            )
        )

        TextField(
            value = description,
            onValueChange = onUpdateSheetTaskDescription,
            placeholder = { Text(text = stringResource(R.string.description)) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 17.dp)
        ) {
            IconButton(onClick = showDatePicker) {
                Icon(imageVector = Icons.Outlined.Timer, contentDescription = null)
            }

            IconButton(onClick = showCategoryPicker) {
                Icon(imageVector = Icons.Outlined.Category, contentDescription = null)
            }

            IconButton(onClick = showPriorityPicker) {
                Icon(imageVector = Icons.Outlined.Flag, contentDescription = null)
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = onCreateTask) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Send,
                    contentDescription = null
                )
            }
        }
    }
}