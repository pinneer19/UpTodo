package dev.uptodo.app.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.uptodo.domain.model.Task
import dev.uptodo.ui.theme.UpTodoTheme

@Composable
fun HomeScreen(tasks: List<Task>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.FilterList, null)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text("Index")
            Spacer(modifier = Modifier.weight(1f))
        }
        OutlinedTextField(value = "", onValueChange = {})
        Button(onClick = { /*TODO*/ }) {
            Text("Today")
            Icon(Icons.Default.KeyboardArrowDown, null)
        }

        TaskList(tasks = tasks, modifier = Modifier.fillMaxWidth())
    }

}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    UpTodoTheme {
        HomeScreen(emptyList())
    }
}