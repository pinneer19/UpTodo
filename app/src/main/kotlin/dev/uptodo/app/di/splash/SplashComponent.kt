package dev.uptodo.app.di.splash

import dagger.Subcomponent
import dev.uptodo.app.ui.screens.splash.SplashViewModel

@Subcomponent(modules = [SplashModule::class])
@SplashScope
interface SplashComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): SplashComponent
    }

    fun getViewModel(): SplashViewModel
}