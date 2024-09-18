package dev.uptodo.app.ui.screens.task

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.uptodo.app.R
import kotlin.math.roundToInt

@Composable
fun ExpandableFab(modifier: Modifier = Modifier, actions: List<FabAction>) {

    var expanded by remember {
        mutableStateOf(false)
    }

    val textOffset by animateDpAsState(
        animationSpec = spring(dampingRatio = 3f),
        targetValue = if (expanded) 20.dp else 50.dp,
        label = "Text offset animation"
    )

    val textAlpha by animateFloatAsState(
        animationSpec = tween(durationMillis = 300),
        targetValue = if (expanded) 1f else 0f,
        label = "Text offset animation"
    )

    val iconOffset by animateDpAsState(
        animationSpec = spring(dampingRatio = 3f),
        targetValue = if (expanded) (-170).dp else 0.dp,
        label = "Icon offset animation"
    )

    val expandedFabWidth by animateDpAsState(
        targetValue = if (expanded) 192.dp else 64.dp,
        animationSpec = spring(dampingRatio = 3f),
        label = "Fab width animation"
    )

    val expandedFabHeight by animateDpAsState(
        targetValue = if (expanded) 58.dp else 64.dp,
        animationSpec = spring(dampingRatio = 3f),
        label = "Fab height animation"
    )

    val actionsListHeight by animateDpAsState(
        targetValue = if (expanded) (actions.size * 77).dp else 0.dp,
        animationSpec = spring(dampingRatio = 4f),
        label = "Actions list height animation"
    )

    Column(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .offset(y = (25).dp)
                .size(
                    width = expandedFabWidth,
                    height = actionsListHeight
                )
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(18.dp)
                )
                .clip(RoundedCornerShape(18.dp))
        ) {
            actions.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 10.dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            expanded = !expanded
                            it.onAction()
                        }
                        .padding(20.dp)
                ) {
                    Icon(
                        imageVector = it.icon,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )

                    Text(text = it.name, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .width(expandedFabWidth)
                .height(expandedFabHeight),
            onClick = { expanded = !expanded }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                modifier = Modifier.offset {
                    IntOffset(x = iconOffset.value.roundToInt(), y = 0)
                },
                contentDescription = "Expand button"
            )

            if (expanded) {
                Text(
                    text = stringResource(R.string.choose_action),
                    modifier = Modifier
                        .offset {
                            IntOffset(x = textOffset.value.roundToInt(), y = 0)
                        }
                        .alpha(textAlpha),
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun ExpandableFabPreview() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExpandableFab(
                actions = listOf(
                    FabAction("Home", Icons.Default.Home, {}),
                    FabAction("Home", Icons.Default.Home, {}),
                )
            )
        }
    ) {}
}