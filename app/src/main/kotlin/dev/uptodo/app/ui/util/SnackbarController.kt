package dev.uptodo.app.ui.util

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackbarEvent(
    val message: String,
    val action: SnackbarAction? = null
)

data class SnackbarAction(
    val name: String,
    val action: suspend () -> Unit
)

object SnackbarController {
    private val _events = Channel<SnackbarEvent>()
    val events: Flow<SnackbarEvent> = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackbarEvent) {
        _events.send(event)
    }

    suspend fun sendMessageEvent(message: String) {
        _events.send(SnackbarEvent(message = message))
    }
}