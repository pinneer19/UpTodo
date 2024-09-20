package dev.uptodo.app.ui.screens.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.uptodo.app.ui.util.darken
import dev.uptodo.app.ui.util.decodeMaterialIcon
import dev.uptodo.app.ui.util.fromHex
import dev.uptodo.app.util.toUiString
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.model.TaskPriority
import kotlinx.datetime.LocalDateTime

@Composable
fun BoxScope.TaskItemContent(
    name: String,
    deadline: LocalDateTime,
    category: TaskCategory?,
    priority: TaskPriority,
    isExpandable: Boolean,
    showSubtasks: Boolean,
    onExpandItem: () -> Unit
) {
    val expandIconDegrees by animateFloatAsState(
        targetValue = if (showSubtasks) 0f else -180f,
        animationSpec = tween(500),
        label = "Show subtasks icon rotation"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                ),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = deadline.toUiString(),
                modifier = Modifier.padding(top = 6.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }

    Row(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(bottom = 5.dp, end = 10.dp)
            .then(
                if (isExpandable) Modifier.padding(end = 40.dp) else Modifier
            ),
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

    if (isExpandable) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 15.dp)
                .rotate(expandIconDegrees)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = onExpandItem
                ),
            contentDescription = null
        )
    }
}