package dev.uptodo.presentation.screens.task

import dev.uptodo.app.ui.theme.UpTodoTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.SubdirectoryArrowRight
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uptodo.ui.screens.task.TaskProperty

@Composable
fun TaskScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 15.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 30.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.Repeat, contentDescription = null)
        }

        Component(
            name = "Do math homework",
            description = "Do chapter 2 to 5 for next week",
            modifier = Modifier.fillMaxWidth()
        )

        TaskProperty(
            name = "Task time",
            value = "Today at 16:45",
            icon = Icons.Outlined.Timer,
            onPropertyClicked = {}
        )
        TaskProperty(
            name = "Task category",
            value = "University",
            icon = Icons.Outlined.Category,
            valueIcon = Icons.Outlined.School,
            onPropertyClicked = {}
        )
        TaskProperty(
            name = "Task priority",
            value = "Default",
            icon = Icons.Outlined.Flag,
            onPropertyClicked = {}
        )
        TaskProperty(
            name = "Sub-Task",
            value = "Add Sub-Task",
            icon = Icons.Default.SubdirectoryArrowRight,
            onPropertyClicked = {}
        )

        TextButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Outlined.Delete, null, Modifier.padding(end = 10.dp))
            Text("Delete task")
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Edit task")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskScreenPreview() {
    UpTodoTheme {
        TaskScreen()
    }
}

@Composable
fun Component(
    name: String,
    description: String,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.Circle,
    editIcon: ImageVector = Icons.Outlined.Edit,
) {
    Row(modifier = modifier) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(end = 10.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(text = name)
            Text(text = description)
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = editIcon, contentDescription = null)
    }
}