package dev.uptodo.app.util.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import dev.uptodo.app.R
import dev.uptodo.app.ui.util.toUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class VoiceListener @Inject constructor(
    val context: Context
) : RecognitionListener {

    private var _state = MutableStateFlow(VoiceListenerState())
    val state: StateFlow<VoiceListenerState> = _state

    private val recognizer = SpeechRecognizer.createSpeechRecognizer(context)

    fun startListening(languageCode: String) {
        _state.update { VoiceListenerState() }

        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            _state.update {
                it.copy(
                    error = R.string.recognition_is_unavailable.toUiText()
                )
            }
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
        }

        recognizer.setRecognitionListener(this)
        recognizer.startListening(intent)

        _state.update { it.copy(speaking = true) }
    }

    fun stopListening() {
        _state.update { it.copy(speaking = false) }
        recognizer.stopListening()
    }

    override fun onReadyForSpeech(params: Bundle?) {
        _state.update { it.copy(error = null) }
    }

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(rmsdB: Float) = Unit

    override fun onBufferReceived(buffer: ByteArray?) = Unit

    override fun onEndOfSpeech() {
        _state.update { it.copy(speaking = false) }
        recognizer.stopListening()
    }

    override fun onError(error: Int) {
        val errorMessageResId: Int = when (error) {

            SpeechRecognizer.ERROR_CLIENT -> return

            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> R.string.network_timeout_error

            SpeechRecognizer.ERROR_NETWORK -> R.string.network_error

            SpeechRecognizer.ERROR_AUDIO -> R.string.audio_error

            SpeechRecognizer.ERROR_SERVER -> R.string.server_error

            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> R.string.speech_timeout_error

            SpeechRecognizer.ERROR_NO_MATCH -> R.string.no_match_error

            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> R.string.recognizer_busy_error

            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> R.string.insufficient_permissions_error

            SpeechRecognizer.ERROR_TOO_MANY_REQUESTS -> R.string.too_many_requests_error

            SpeechRecognizer.ERROR_SERVER_DISCONNECTED -> R.string.server_disconnected_error

            SpeechRecognizer.ERROR_LANGUAGE_NOT_SUPPORTED -> R.string.language_not_supported_error

            SpeechRecognizer.ERROR_LANGUAGE_UNAVAILABLE -> R.string.language_unavailable_error

            SpeechRecognizer.ERROR_CANNOT_CHECK_SUPPORT -> R.string.cannot_check_support_error

            SpeechRecognizer.ERROR_CANNOT_LISTEN_TO_DOWNLOAD_EVENTS -> R.string.cannot_listen_to_download_events_error

            else -> R.string.unknown_error
        }

        _state.update {
            it.copy(
                error = errorMessageResId.toUiText(),
                speaking = false
            )
        }
    }

    override fun onResults(results: Bundle?) {
        results
            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { text ->
                _state.update { it.copy(spokenText = text, error = null) }
                println(text)
            }
    }

    override fun onPartialResults(partialResults: Bundle?) = Unit

    override fun onEvent(eventType: Int, params: Bundle?) = Unit
}