package dev.uptodo.app.di

import android.speech.RecognitionListener
import dagger.Binds
import dagger.Module
import dev.uptodo.app.util.voice.VoiceListener

@Module
interface AppBindModule {

    @Binds
    fun bindVoiceListener(listener: VoiceListener): RecognitionListener
}