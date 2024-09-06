package dev.uptodo.presentation.screens.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.uptodo.app.ui.screens.category.Colors
import dev.uptodo.app.ui.theme.UpTodoTheme

@Composable
fun CategoryScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(start = 24.dp, end = 24.dp, top = 26.dp, bottom = 46.dp),
    ) {
        Text(
            text = "Create new category",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Name",
            modifier = Modifier.padding(top = 20.dp, bottom = 16.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(6.dp),
            singleLine = true
        )

        Text(
            text = "Icon",
            modifier = Modifier.padding(top = 20.dp, bottom = 16.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Choose icon from library")
        }

        Text(
            text = "Color",
            modifier = Modifier.padding(top = 20.dp, bottom = 16.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(Colors) { color ->
                Box(
                    Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(color))
                ) {}
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.size(width = 140.dp, height = 45.dp)
            ) {
                Text(text = "Cancel")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.size(width = 140.dp, height = 45.dp)
            ) {
                Text(text = "Create")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    UpTodoTheme(darkTheme = true) {
        CategoryScreen()
    }
}