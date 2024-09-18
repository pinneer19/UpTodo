package dev.uptodo.app.ui.screens.category

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun SearchTextField(
    onSearchChanged: (String) -> Unit
) {
    var search by remember { mutableStateOf("") }

    OutlinedTextField(
        label = {
            Text(
                text = "Search",
                color = MaterialTheme.colorScheme.primary
            )
        },
        value = search,
        onValueChange = {
            search = it
            onSearchChanged(it)
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.primary
        ),
        singleLine = true
    )
}