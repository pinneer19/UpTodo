package dev.uptodo.app.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.uptodo.app.util.darken
import dev.uptodo.ui.theme.UpTodoTheme

data class Category(val name: String, val iconUri: String, val iconTint: String)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryPicker(
    categories: List<Category>,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Choose category", modifier = Modifier.padding(vertical = 10.dp))
            HorizontalDivider(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
            )
            FlowRow(
                modifier = modifier.fillMaxWidth(),
                maxItemsInEachRow = 3,
                horizontalArrangement = Arrangement.SpaceAround,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                categories.forEach {
                    Category(
                        imageVector = Icons.Outlined.HealthAndSafety,
                        tint = Color.Green,
                        onClick = {}
                    )
                }

                Category(
                    imageVector = Icons.Outlined.HealthAndSafety,
                    tint = Color.Green,
                    onClick = {}
                )
            }

            Row(Modifier.fillMaxWidth().padding(top = 20.dp), horizontalArrangement = Arrangement.SpaceAround) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Cancel")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Edit")
                }
            }
        }
    }
}


@Composable
fun Category(
    imageVector: ImageVector,
    tint: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(color = tint, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = tint.darken(0.5f)
            )
        }
        Text("Work", Modifier.padding(top = 5.dp))
    }
}


@Preview
@Composable
fun CategoryPreview() {
    Category(
        imageVector = Icons.Outlined.WorkOutline,
        tint = Color.Green.copy(alpha = 0.9f),
        onClick = {}
    )
}

@Preview
@Composable
fun CategoryPickerPreview() {
    UpTodoTheme {
        CategoryPicker(
            categories = listOf(
                Category(name = "Work", iconUri = "", iconTint = ""),
                Category(name = "Work", iconUri = "", iconTint = ""),
                Category(name = "Work", iconUri = "", iconTint = ""),
                Category(name = "Work", iconUri = "", iconTint = ""),
                Category(name = "Work", iconUri = "", iconTint = ""),
                Category(name = "Work", iconUri = "", iconTint = ""),

                ),
            onDismissRequest = {}
        )
    }
}