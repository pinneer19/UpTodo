package uptodo.ui.screens.task

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.uptodo.app.ui.theme.UpTodoTheme

@Composable
fun TaskProperty(
    name: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    valueIcon: ImageVector? = null,
    onPropertyClicked: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(end = 10.dp)
        )
        Text(text = name)
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onPropertyClicked,
            shape = RoundedCornerShape(8.dp)
        ) {
            valueIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
            Text(value)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskPropertyPreview() {
    UpTodoTheme {
        TaskProperty(
            name = "Homework",
            value = "Today",
            icon = Icons.Default.Add,
            modifier = Modifier.fillMaxWidth(),
            onPropertyClicked = {},
            valueIcon = Icons.Default.Call
        )
    }
}