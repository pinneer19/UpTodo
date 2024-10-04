package dev.uptodo.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.uptodo.app.ui.theme.UpTodoTheme

@Composable
fun TextFieldComponent(
    value: String,
    label: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    trailingIcon:  @Composable ((String) -> Unit),
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    shape: Shape = RoundedCornerShape(10.dp),

) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f)
            ),
            textStyle = MaterialTheme.typography.bodyLarge,
            value = value,
            enabled = enabled,
            singleLine = true,
            placeholder = {
                Text(text = placeholder)
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            onValueChange = onValueChange,
            visualTransformation = visualTransformation,
            shape = shape,
            trailingIcon = {
                trailingIcon(value)
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TextFieldComponentPreview() {
    UpTodoTheme {
        TextFieldComponent(
            value = "Sample",
            label = "Username",
            placeholder = "Enter your username",
            enabled = true,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {},
            trailingIcon = {}
        )
    }
}