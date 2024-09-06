package dev.uptodo.app.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.EventNote
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.uptodo.app.R
import dev.uptodo.domain.model.Task

@Composable
fun TaskList(
    todoTasks: List<Pair<String, Task>>,
    completedTasks: List<Pair<String, Task>>,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
    fabHeight: Dp = 56.dp
) {
    var todoTasksExpanded by remember { mutableStateOf(true) }
    var completedTasksExpanded by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 20.dp + fabHeight)
    ) {
        item(key = "Todo header") {
            GroupHeader(
                header = stringResource(R.string.today),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .animateItem(),
                onClick = { todoTasksExpanded = !todoTasksExpanded },
                expanded = todoTasksExpanded
            )
        }

        item(key = "Empty todo tasks label") {
            AnimatedVisibility(
                visible = todoTasks.isEmpty() && todoTasksExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(64.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = stringResource(R.string.all_is_done),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        items(
            items = todoTasks,
            key = { it.first }
        ) {
            AnimatedVisibility(
                modifier = Modifier.animateItem(),
                visible = todoTasksExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                TaskItem(
                    task = it.second,
                    modifier = Modifier.padding(bottom = 10.dp),
                    onCompleteTask = { onEvent(HomeEvent.ToggleTaskCompleteState(taskId = it.first)) },
                    onDeleteTask = { onEvent(HomeEvent.DeleteTask(taskId = it.first)) }
                )
            }
        }

        item(key = "Completed header") {
            GroupHeader(
                header = stringResource(R.string.completed),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .animateItem(),
                onClick = { completedTasksExpanded = !completedTasksExpanded },
                expanded = completedTasksExpanded
            )
        }

        item(key = "Empty completed tasks label") {
            AnimatedVisibility(
                visible = completedTasks.isEmpty() && completedTasksExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.EventNote,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(64.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = stringResource(R.string.check_your_todo_tasks),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        items(
            items = completedTasks,
            key = { it.first }
        ) {
            AnimatedVisibility(
                modifier = Modifier.animateItem(),
                visible = completedTasksExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                TaskItem(
                    task = it.second,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .animateItem(),
                    onCompleteTask = { onEvent(HomeEvent.ToggleTaskCompleteState(taskId = it.first)) },
                    onDeleteTask = { onEvent(HomeEvent.DeleteTask(taskId = it.first)) }
                )
            }
        }
    }
}