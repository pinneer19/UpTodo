package dev.uptodo.app.ui.screens.category.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.uptodo.app.R

@Composable
fun SearchTextField(
    searchInput: String,
    onSearchChanged: (String) -> Unit
) {
    OutlinedTextField(
        label = {
            Text(
                text = stringResource(R.string.search),
                color = MaterialTheme.colorScheme.primary
            )
        },
        value = searchInput,
        onValueChange = onSearchChanged,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.primary
        ),
        singleLine = true
    )
}