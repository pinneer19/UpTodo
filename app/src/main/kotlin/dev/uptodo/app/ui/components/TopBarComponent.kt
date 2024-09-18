package dev.uptodo.app.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TopBarComponent(
    title: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    leadingAction: (() -> Unit)? = null,
    trailingIcon: ImageVector? = null,
    trailingAction: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 15.dp, end = 10.dp, bottom = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon?.let { icon ->
            IconButton(
                onClick = requireNotNull(leadingAction),
                modifier = Modifier.padding(end = 15.dp)
            ) {
                Icon(imageVector = icon, contentDescription = null)
            }
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.weight(1f))

        trailingIcon?.let { icon ->
            IconButton(onClick = requireNotNull(trailingAction)) {
                Icon(imageVector = icon, contentDescription = null)
            }
        }
    }
}