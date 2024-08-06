package dev.uptodo.app.model

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAuthState = staticCompositionLocalOf<AuthState> {
    error("No AuthState provided")
}