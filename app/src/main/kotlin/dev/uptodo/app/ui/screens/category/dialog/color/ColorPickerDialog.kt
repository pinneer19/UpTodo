package dev.uptodo.app.ui.screens.category.dialog.color

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import dev.uptodo.app.R
import dev.uptodo.app.ui.screens.category.viewmodel.CategoryEvent

@Composable
fun ColorPickerDialog(
    colorState: Color?,
    onEvent: (CategoryEvent) -> Unit,
    onDismissRequest: () -> Unit
) {
    val controller = rememberColorPickerController()
    var color by remember {
        mutableStateOf(colorState)
    }

    Dialog(
        onDismissRequest = {
            onEvent(CategoryEvent.UpdateCategoryColor(color))
            onDismissRequest()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HsvColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp),
                controller = controller,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    color = colorEnvelope.color
                },
                initialColor = color
            )

            AlphaSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(35.dp),
                controller = controller,
                initialColor = color
            )

            BrightnessSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(35.dp),
                controller = controller,
                initialColor = color
            )

            Box(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .size(64.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(
                        color = color ?: Color.Unspecified
                    )
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                ),
                modifier = Modifier
                    .widthIn(min = 120.dp)
                    .padding(bottom = 50.dp),
                onClick = {
                    onEvent(CategoryEvent.UpdateCategoryColor(color))
                    onDismissRequest()
                }
            ) {
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(id = R.string.ok),
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}