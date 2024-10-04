package dev.uptodo.app.util.voice

import dev.uptodo.app.ui.util.UiText

data class VoiceListenerState(
    val spokenText: String = "",
    val speaking: Boolean = false,
    val error: UiText? = null
)