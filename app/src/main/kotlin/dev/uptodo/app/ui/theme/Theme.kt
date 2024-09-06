package dev.uptodo.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import dev.uptodo.ui.theme.backgroundDark
import dev.uptodo.ui.theme.backgroundDarkHighContrast
import dev.uptodo.ui.theme.backgroundDarkMediumContrast
import dev.uptodo.ui.theme.backgroundLight
import dev.uptodo.ui.theme.backgroundLightHighContrast
import dev.uptodo.ui.theme.backgroundLightMediumContrast
import dev.uptodo.ui.theme.errorContainerDark
import dev.uptodo.ui.theme.errorContainerDarkHighContrast
import dev.uptodo.ui.theme.errorContainerDarkMediumContrast
import dev.uptodo.ui.theme.errorContainerLight
import dev.uptodo.ui.theme.errorContainerLightHighContrast
import dev.uptodo.ui.theme.errorContainerLightMediumContrast
import dev.uptodo.ui.theme.errorDark
import dev.uptodo.ui.theme.errorDarkHighContrast
import dev.uptodo.ui.theme.errorDarkMediumContrast
import dev.uptodo.ui.theme.errorLight
import dev.uptodo.ui.theme.errorLightHighContrast
import dev.uptodo.ui.theme.errorLightMediumContrast
import dev.uptodo.ui.theme.inverseOnSurfaceDark
import dev.uptodo.ui.theme.inverseOnSurfaceDarkHighContrast
import dev.uptodo.ui.theme.inverseOnSurfaceDarkMediumContrast
import dev.uptodo.ui.theme.inverseOnSurfaceLight
import dev.uptodo.ui.theme.inverseOnSurfaceLightHighContrast
import dev.uptodo.ui.theme.inverseOnSurfaceLightMediumContrast
import dev.uptodo.ui.theme.inversePrimaryDark
import dev.uptodo.ui.theme.inversePrimaryDarkHighContrast
import dev.uptodo.ui.theme.inversePrimaryDarkMediumContrast
import dev.uptodo.ui.theme.inversePrimaryLight
import dev.uptodo.ui.theme.inversePrimaryLightHighContrast
import dev.uptodo.ui.theme.inversePrimaryLightMediumContrast
import dev.uptodo.ui.theme.inverseSurfaceDark
import dev.uptodo.ui.theme.inverseSurfaceDarkHighContrast
import dev.uptodo.ui.theme.inverseSurfaceDarkMediumContrast
import dev.uptodo.ui.theme.inverseSurfaceLight
import dev.uptodo.ui.theme.inverseSurfaceLightHighContrast
import dev.uptodo.ui.theme.inverseSurfaceLightMediumContrast
import dev.uptodo.ui.theme.onBackgroundDark
import dev.uptodo.ui.theme.onBackgroundDarkHighContrast
import dev.uptodo.ui.theme.onBackgroundDarkMediumContrast
import dev.uptodo.ui.theme.onBackgroundLight
import dev.uptodo.ui.theme.onBackgroundLightHighContrast
import dev.uptodo.ui.theme.onBackgroundLightMediumContrast
import dev.uptodo.ui.theme.onErrorContainerDark
import dev.uptodo.ui.theme.onErrorContainerDarkHighContrast
import dev.uptodo.ui.theme.onErrorContainerDarkMediumContrast
import dev.uptodo.ui.theme.onErrorContainerLight
import dev.uptodo.ui.theme.onErrorContainerLightHighContrast
import dev.uptodo.ui.theme.onErrorContainerLightMediumContrast
import dev.uptodo.ui.theme.onErrorDark
import dev.uptodo.ui.theme.onErrorDarkHighContrast
import dev.uptodo.ui.theme.onErrorDarkMediumContrast
import dev.uptodo.ui.theme.onErrorLight
import dev.uptodo.ui.theme.onErrorLightHighContrast
import dev.uptodo.ui.theme.onErrorLightMediumContrast
import dev.uptodo.ui.theme.onPrimaryContainerDark
import dev.uptodo.ui.theme.onPrimaryContainerDarkHighContrast
import dev.uptodo.ui.theme.onPrimaryContainerDarkMediumContrast
import dev.uptodo.ui.theme.onPrimaryContainerLight
import dev.uptodo.ui.theme.onPrimaryContainerLightHighContrast
import dev.uptodo.ui.theme.onPrimaryContainerLightMediumContrast
import dev.uptodo.ui.theme.onPrimaryDark
import dev.uptodo.ui.theme.onPrimaryDarkHighContrast
import dev.uptodo.ui.theme.onPrimaryDarkMediumContrast
import dev.uptodo.ui.theme.onPrimaryLight
import dev.uptodo.ui.theme.onPrimaryLightHighContrast
import dev.uptodo.ui.theme.onPrimaryLightMediumContrast
import dev.uptodo.ui.theme.onSecondaryContainerDark
import dev.uptodo.ui.theme.onSecondaryContainerDarkHighContrast
import dev.uptodo.ui.theme.onSecondaryContainerDarkMediumContrast
import dev.uptodo.ui.theme.onSecondaryContainerLight
import dev.uptodo.ui.theme.onSecondaryContainerLightHighContrast
import dev.uptodo.ui.theme.onSecondaryContainerLightMediumContrast
import dev.uptodo.ui.theme.onSecondaryDark
import dev.uptodo.ui.theme.onSecondaryDarkHighContrast
import dev.uptodo.ui.theme.onSecondaryDarkMediumContrast
import dev.uptodo.ui.theme.onSecondaryLight
import dev.uptodo.ui.theme.onSecondaryLightHighContrast
import dev.uptodo.ui.theme.onSecondaryLightMediumContrast
import dev.uptodo.ui.theme.onSurfaceDark
import dev.uptodo.ui.theme.onSurfaceDarkHighContrast
import dev.uptodo.ui.theme.onSurfaceDarkMediumContrast
import dev.uptodo.ui.theme.onSurfaceLight
import dev.uptodo.ui.theme.onSurfaceLightHighContrast
import dev.uptodo.ui.theme.onSurfaceLightMediumContrast
import dev.uptodo.ui.theme.onSurfaceVariantDark
import dev.uptodo.ui.theme.onSurfaceVariantDarkHighContrast
import dev.uptodo.ui.theme.onSurfaceVariantDarkMediumContrast
import dev.uptodo.ui.theme.onSurfaceVariantLight
import dev.uptodo.ui.theme.onSurfaceVariantLightHighContrast
import dev.uptodo.ui.theme.onSurfaceVariantLightMediumContrast
import dev.uptodo.ui.theme.onTertiaryContainerDark
import dev.uptodo.ui.theme.onTertiaryContainerDarkHighContrast
import dev.uptodo.ui.theme.onTertiaryContainerDarkMediumContrast
import dev.uptodo.ui.theme.onTertiaryContainerLight
import dev.uptodo.ui.theme.onTertiaryContainerLightHighContrast
import dev.uptodo.ui.theme.onTertiaryContainerLightMediumContrast
import dev.uptodo.ui.theme.onTertiaryDark
import dev.uptodo.ui.theme.onTertiaryDarkHighContrast
import dev.uptodo.ui.theme.onTertiaryDarkMediumContrast
import dev.uptodo.ui.theme.onTertiaryLight
import dev.uptodo.ui.theme.onTertiaryLightHighContrast
import dev.uptodo.ui.theme.onTertiaryLightMediumContrast
import dev.uptodo.ui.theme.outlineDark
import dev.uptodo.ui.theme.outlineDarkHighContrast
import dev.uptodo.ui.theme.outlineDarkMediumContrast
import dev.uptodo.ui.theme.outlineLight
import dev.uptodo.ui.theme.outlineLightHighContrast
import dev.uptodo.ui.theme.outlineLightMediumContrast
import dev.uptodo.ui.theme.outlineVariantDark
import dev.uptodo.ui.theme.outlineVariantDarkHighContrast
import dev.uptodo.ui.theme.outlineVariantDarkMediumContrast
import dev.uptodo.ui.theme.outlineVariantLight
import dev.uptodo.ui.theme.outlineVariantLightHighContrast
import dev.uptodo.ui.theme.outlineVariantLightMediumContrast
import dev.uptodo.ui.theme.primaryContainerDark
import dev.uptodo.ui.theme.primaryContainerDarkHighContrast
import dev.uptodo.ui.theme.primaryContainerDarkMediumContrast
import dev.uptodo.ui.theme.primaryContainerLight
import dev.uptodo.ui.theme.primaryContainerLightHighContrast
import dev.uptodo.ui.theme.primaryContainerLightMediumContrast
import dev.uptodo.ui.theme.primaryDark
import dev.uptodo.ui.theme.primaryDarkHighContrast
import dev.uptodo.ui.theme.primaryDarkMediumContrast
import dev.uptodo.ui.theme.primaryLight
import dev.uptodo.ui.theme.primaryLightHighContrast
import dev.uptodo.ui.theme.primaryLightMediumContrast
import dev.uptodo.ui.theme.scrimDark
import dev.uptodo.ui.theme.scrimDarkHighContrast
import dev.uptodo.ui.theme.scrimDarkMediumContrast
import dev.uptodo.ui.theme.scrimLight
import dev.uptodo.ui.theme.scrimLightHighContrast
import dev.uptodo.ui.theme.scrimLightMediumContrast
import dev.uptodo.ui.theme.secondaryContainerDark
import dev.uptodo.ui.theme.secondaryContainerDarkHighContrast
import dev.uptodo.ui.theme.secondaryContainerDarkMediumContrast
import dev.uptodo.ui.theme.secondaryContainerLight
import dev.uptodo.ui.theme.secondaryContainerLightHighContrast
import dev.uptodo.ui.theme.secondaryContainerLightMediumContrast
import dev.uptodo.ui.theme.secondaryDark
import dev.uptodo.ui.theme.secondaryDarkHighContrast
import dev.uptodo.ui.theme.secondaryDarkMediumContrast
import dev.uptodo.ui.theme.secondaryLight
import dev.uptodo.ui.theme.secondaryLightHighContrast
import dev.uptodo.ui.theme.secondaryLightMediumContrast
import dev.uptodo.ui.theme.surfaceBrightDark
import dev.uptodo.ui.theme.surfaceBrightDarkHighContrast
import dev.uptodo.ui.theme.surfaceBrightDarkMediumContrast
import dev.uptodo.ui.theme.surfaceBrightLight
import dev.uptodo.ui.theme.surfaceBrightLightHighContrast
import dev.uptodo.ui.theme.surfaceBrightLightMediumContrast
import dev.uptodo.ui.theme.surfaceContainerDark
import dev.uptodo.ui.theme.surfaceContainerDarkHighContrast
import dev.uptodo.ui.theme.surfaceContainerDarkMediumContrast
import dev.uptodo.ui.theme.surfaceContainerHighDark
import dev.uptodo.ui.theme.surfaceContainerHighDarkHighContrast
import dev.uptodo.ui.theme.surfaceContainerHighDarkMediumContrast
import dev.uptodo.ui.theme.surfaceContainerHighLight
import dev.uptodo.ui.theme.surfaceContainerHighLightHighContrast
import dev.uptodo.ui.theme.surfaceContainerHighLightMediumContrast
import dev.uptodo.ui.theme.surfaceContainerHighestDark
import dev.uptodo.ui.theme.surfaceContainerHighestDarkHighContrast
import dev.uptodo.ui.theme.surfaceContainerHighestDarkMediumContrast
import dev.uptodo.ui.theme.surfaceContainerHighestLight
import dev.uptodo.ui.theme.surfaceContainerHighestLightHighContrast
import dev.uptodo.ui.theme.surfaceContainerHighestLightMediumContrast
import dev.uptodo.ui.theme.surfaceContainerLight
import dev.uptodo.ui.theme.surfaceContainerLightHighContrast
import dev.uptodo.ui.theme.surfaceContainerLightMediumContrast
import dev.uptodo.ui.theme.surfaceContainerLowDark
import dev.uptodo.ui.theme.surfaceContainerLowDarkHighContrast
import dev.uptodo.ui.theme.surfaceContainerLowDarkMediumContrast
import dev.uptodo.ui.theme.surfaceContainerLowLight
import dev.uptodo.ui.theme.surfaceContainerLowLightHighContrast
import dev.uptodo.ui.theme.surfaceContainerLowLightMediumContrast
import dev.uptodo.ui.theme.surfaceContainerLowestDark
import dev.uptodo.ui.theme.surfaceContainerLowestDarkHighContrast
import dev.uptodo.ui.theme.surfaceContainerLowestDarkMediumContrast
import dev.uptodo.ui.theme.surfaceContainerLowestLight
import dev.uptodo.ui.theme.surfaceContainerLowestLightHighContrast
import dev.uptodo.ui.theme.surfaceContainerLowestLightMediumContrast
import dev.uptodo.ui.theme.surfaceDark
import dev.uptodo.ui.theme.surfaceDarkHighContrast
import dev.uptodo.ui.theme.surfaceDarkMediumContrast
import dev.uptodo.ui.theme.surfaceDimDark
import dev.uptodo.ui.theme.surfaceDimDarkHighContrast
import dev.uptodo.ui.theme.surfaceDimDarkMediumContrast
import dev.uptodo.ui.theme.surfaceDimLight
import dev.uptodo.ui.theme.surfaceDimLightHighContrast
import dev.uptodo.ui.theme.surfaceDimLightMediumContrast
import dev.uptodo.ui.theme.surfaceLight
import dev.uptodo.ui.theme.surfaceLightHighContrast
import dev.uptodo.ui.theme.surfaceLightMediumContrast
import dev.uptodo.ui.theme.surfaceVariantDark
import dev.uptodo.ui.theme.surfaceVariantDarkHighContrast
import dev.uptodo.ui.theme.surfaceVariantDarkMediumContrast
import dev.uptodo.ui.theme.surfaceVariantLight
import dev.uptodo.ui.theme.surfaceVariantLightHighContrast
import dev.uptodo.ui.theme.surfaceVariantLightMediumContrast
import dev.uptodo.ui.theme.tertiaryContainerDark
import dev.uptodo.ui.theme.tertiaryContainerDarkHighContrast
import dev.uptodo.ui.theme.tertiaryContainerDarkMediumContrast
import dev.uptodo.ui.theme.tertiaryContainerLight
import dev.uptodo.ui.theme.tertiaryContainerLightHighContrast
import dev.uptodo.ui.theme.tertiaryContainerLightMediumContrast
import dev.uptodo.ui.theme.tertiaryDark
import dev.uptodo.ui.theme.tertiaryDarkHighContrast
import dev.uptodo.ui.theme.tertiaryDarkMediumContrast
import dev.uptodo.ui.theme.tertiaryLight
import dev.uptodo.ui.theme.tertiaryLightHighContrast
import dev.uptodo.ui.theme.tertiaryLightMediumContrast

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

@Composable
fun UpTodoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> darkScheme
        else -> lightScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            window.navigationBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}