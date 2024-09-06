package dev.uptodo.app.util

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor: Color = Color.Transparent,
    placeholder: @Composable (() -> Unit)? = null
) {
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .border(
                width = if (isFocused) 1.dp else 0.dp,
                color = if (isFocused) focusedBorderColor else unfocusedBorderColor,
                shape = shape
            )
            .padding(4.dp) // Adjust padding if needed
    ) {

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .onFocusChanged { focusState -> isFocused = focusState.isFocused }
                .clip(shape), // Clip to avoid overflow outside the border
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default,
            singleLine = true,
            cursorBrush = SolidColor(focusedBorderColor),
            decorationBox = { innerTextField ->
                if (value.isEmpty() && placeholder != null) {
                    placeholder()
                }
                innerTextField() // Draw the actual text field
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ExampleScreen() {
    var text by remember { mutableStateOf("") }

    Spacer(modifier = Modifier.height(100.dp))
    CustomOutlinedTextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text(text = "Enter text...") },
        modifier = Modifier.fillMaxWidth()

    )
    Spacer(modifier = Modifier.height(100.dp))
}