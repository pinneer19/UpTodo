package dev.uptodo.app.ui.screens.home.tasklist

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.uptodo.app.ui.theme.UpTodoTheme
import dev.uptodo.domain.model.Task
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.model.TaskPriority
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt

@Composable
fun TaskItem(
    task: Task,
    onCompleteTask: () -> Unit,
    onDeleteTask: () -> Unit,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = { state ->
            when (state) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onCompleteTask()
                    true
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    onDeleteTask()
                    true
                }

                else -> false
            }
        }
    )

    var showSubtasks by remember { mutableStateOf(false) }

    val (itemStartToEndSwipeIcon, itemBackground) = when {
        task.completed -> Icons.Outlined.Clear to
                MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.8f)

        else -> Icons.Outlined.Check to MaterialTheme.colorScheme.primaryContainer
    }

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        backgroundContent = {
            val (alignment: Alignment, icon: ImageVector?) =
                when (swipeToDismissBoxState.dismissDirection) {
                    SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd to Icons.Outlined.Delete
                    SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart to itemStartToEndSwipeIcon
                    SwipeToDismissBoxValue.Settled -> Alignment.Center to null
                }

            val iconOffsetX = remember { Animatable(0f) }

            LaunchedEffect(swipeToDismissBoxState.dismissDirection) {
                val initialX = when (swipeToDismissBoxState.dismissDirection) {
                    SwipeToDismissBoxValue.StartToEnd -> -500f
                    SwipeToDismissBoxValue.EndToStart -> 500f
                    SwipeToDismissBoxValue.Settled -> 0f
                }

                iconOffsetX.snapTo(initialX)

                val targetX = 0f

                iconOffsetX.animateTo(
                    targetValue = targetX,
                    animationSpec = tween(durationMillis = 400)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = alignment
            ) {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.offset {
                            IntOffset(x = iconOffsetX.value.roundToInt(), y = 0)
                        },
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(itemBackground)
                .clickable { onItemClick() }
                .animateContentSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                with(task) {
                    if (!completed) {
                        TaskItemContent(
                            name = name,
                            deadline = deadline,
                            category = category,
                            priority = priority,
                            isExpandable = subtasks.isNotEmpty(),
                            showSubtasks = showSubtasks,
                            onExpandItem = { showSubtasks = !showSubtasks }
                        )
                    } else {
                        CompletedTaskItemContent(name = name)
                    }
                }
            }

            if (showSubtasks) {
                SubtaskList(
                    subtasks = task.subtasks,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun TaskPreview() {
    UpTodoTheme {
        TaskItem(
            task = Task(
                name = "Do math",
                description = "",
                category = TaskCategory(
                    name = "Uni",
                    iconUri = "Icons.Default.ShoppingCart",
                    iconTint = "#FF6347"
                ),
                priority = TaskPriority.ONE,
                subtasks = emptyList(),
                deadline = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                completed = false
            ),
            onCompleteTask = {},
            onDeleteTask = {},
            onItemClick = {}
        )
    }
}