package dev.uptodo.presentation.screens.intro

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel

data class OnboardingState(
    val page: Int,
    val title: String,
    val subtitle: String,
    val imagePainter: Painter
)

class OnboardingViewModel: ViewModel() {

}

@Composable
fun OnboardingScreen(

    modifier: Modifier = Modifier
) {
    Column {

    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen()
}