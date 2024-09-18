package dev.uptodo.app.ui.screens.task

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SubdirectoryArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.uptodo.app.R
import dev.uptodo.app.util.keyboardAsState
import dev.uptodo.domain.model.Subtask

@Composable
fun SubtaskProperty(
    subtasks: List<Subtask>,
    onUpdateSubtask: (Int, String) -> Unit,
    onRemoveSubtask: (Int) -> Unit,
    onAddSubtask: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val isKeyboardOpen by keyboardAsState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isKeyboardOpen) {
        if (!isKeyboardOpen) focusManager.clearFocus()
    }

    LaunchedEffect(subtasks.size) {
        if (!lazyListState.isScrolledToTheEnd()) {
            val itemIndex = lazyListState.layoutInfo.totalItemsCount - 1

            if (itemIndex >= 0) {
                val lastItem = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()
                lastItem?.let {
                    lazyListState.animateScrollBy(
                        value = it.size.toFloat() + it.offset,
                        animationSpec = tween(800, delayMillis = 50)
                    )
                }
            }
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.SubdirectoryArrowRight,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Top)
                .padding(end = 10.dp, top = 12.dp)
        )

        if (subtasks.isEmpty()) {
            Text(
                text = stringResource(R.string.subtasks),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.weight(1f))

            TextButton(onClick = onAddSubtask) {
                Text(text = stringResource(id = R.string.add_subtask))
            }
        } else {
            LazyColumn(
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 30.dp),
            ) {
                itemsIndexed(items = subtasks, key = { ind, _ -> ind }) { index, subtask ->
                    SubtaskItem(
                        subtask = subtask,
                        lastItem = index == subtasks.lastIndex,
                        onRemoveSubtask = { onRemoveSubtask(index) },
                        onSubtaskValueChange = { value -> onUpdateSubtask(index, value) }
                    )
                }

                item(key = "Add subtask") {
                    TextButton(onClick = onAddSubtask) {
                        Text(text = stringResource(id = R.string.add_subtask))
                    }
                }
            }
        }
    }
}

fun LazyListState.isScrolledToTheEnd(): Boolean {
    val lastItem = layoutInfo.visibleItemsInfo.lastOrNull()

    return lastItem == null || lastItem.size + lastItem.offset <= layoutInfo.viewportEndOffset
}