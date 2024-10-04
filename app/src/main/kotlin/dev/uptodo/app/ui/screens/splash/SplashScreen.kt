package dev.uptodo.app.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import dev.uptodo.app.R
import dev.uptodo.app.navigation.Route
import dev.uptodo.app.util.Constants.SPLASH_TIMEOUT
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        delay(SPLASH_TIMEOUT)

        viewModel.onAppStart(
            onNavigateToAuthGraph = {
                navController.navigate(Route.AuthGraph) {
                    popUpTo<Route.Splash> { inclusive = true }
                }
            },
            onNavigateToMainGraph = {
                navController.navigate(Route.MainGraph) {
                    popUpTo<Route.Splash> { inclusive = true }
                }
            }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = stringResource(id = R.string.app_logo)
        )
    }
}