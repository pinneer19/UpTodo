package dev.uptodo.app.di.splash

import dagger.Module
import dagger.Provides
import dev.uptodo.app.ui.screens.splash.SplashViewModel
import dev.uptodo.domain.repository.AccountService

@Module
class SplashModule {

    @Provides
    @SplashScope
    fun provideSplashViewModel(accountService: AccountService): SplashViewModel =
        SplashViewModel(accountService)
}