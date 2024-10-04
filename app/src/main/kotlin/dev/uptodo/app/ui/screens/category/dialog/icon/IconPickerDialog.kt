package dev.uptodo.app.ui.screens.category.dialog.icon

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.uptodo.app.R
import dev.uptodo.app.ui.screens.category.component.CardContainer
import dev.uptodo.app.ui.screens.category.component.SearchTextField
import dev.uptodo.app.ui.screens.category.viewmodel.CategoryEvent

@Composable
fun IconPickerDialog(
    searchInput: String,
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
                text = stringResource(R.string.select_icon),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall
            )

            SearchTextField(
                searchInput = searchInput,
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
                    Text(text = stringResource(id = R.string.cancel))
                }

                Button(
                    onClick = {
                        onEvent(CategoryEvent.PickCategoryIcon(selectedIcon))
                        onDismissRequest()
                    }
                ) {
                    Text(text = stringResource(R.string.accept))
                }
            }
        }
    }
}