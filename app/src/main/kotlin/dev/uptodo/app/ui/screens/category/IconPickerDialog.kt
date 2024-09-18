package dev.uptodo.app.ui.screens.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun IconPickerDialog(
    icons: List<List<IconItem>>,
    onDismissRequest: () -> Unit,
    onEvent: (CategoryEvent) -> Unit
) {
    var selectedIcon by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        CardContainer(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .fillMaxHeight(0.7f)
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Select icon",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall
            )

            SearchTextField(
                onSearchChanged = {
                    onEvent(CategoryEvent.UpdateIconPickerSearchInput(it))
                }
            )

            IconsList(
                modifier = Modifier.weight(1f),
                icons = icons,
                selectedIcon = selectedIcon,
                onIconClick = { selectedIcon = it.id }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = onDismissRequest) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        onEvent(CategoryEvent.PickCategoryIcon(selectedIcon))
                        onDismissRequest()
                    }
                ) {
                    Text("Accept")
                }
            }
        }
    }
}