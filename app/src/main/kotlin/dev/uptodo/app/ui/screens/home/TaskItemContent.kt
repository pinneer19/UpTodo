package dev.uptodo.app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.uptodo.app.util.darken
import dev.uptodo.app.util.decodeMaterialIcon
import dev.uptodo.app.util.fromHex
import dev.uptodo.app.util.toUiString
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.model.TaskPriority
import kotlinx.datetime.LocalDateTime

@Composable
fun BoxScope.TaskItemContent(
    name: String,
    deadline: LocalDateTime,
    category: TaskCategory?,
    priority: TaskPriority
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(start = 10.dp, end = 12.dp),
            imageVector = Icons.Outlined.Circle,
            contentDescription = null
        )

        Column(modifier = Modifier) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = deadline.toUiString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }

    Row(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(bottom = 5.dp, end = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        category?.let { category ->
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.fromHex(category.iconTint))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(end = 5.dp),
                    tint = Color
                        .fromHex(category.iconTint)
                        .darken(0.6f),
                    imageVector = decodeMaterialIcon(category.iconUri),
                    contentDescription = null
                )

                Text(
                    text = category.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }

        priority.toInt()?.let { priority ->
            Row(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(end = 3.5.dp),
                    imageVector = Icons.Outlined.Flag,
                    contentDescription = null
                )

                Text(
                    text = priority.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}