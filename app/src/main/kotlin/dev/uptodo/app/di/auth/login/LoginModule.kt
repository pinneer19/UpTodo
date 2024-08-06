package dev.uptodo.app.di.auth.login

import dagger.Module
import dagger.Provides
import dev.uptodo.app.ui.screens.login.LoginViewModel
import dev.uptodo.domain.usecase.LoginWithEmailUseCase
import dev.uptodo.domain.usecase.LoginWithGoogleUseCase
import dev.uptodo.domain.usecase.ValidateLoginInputUseCase

@Module
class LoginModule {

    @Provides
    @LoginScope
    fun provideViewModel(
        validateLoginInputUseCase: ValidateLoginInputUseCase,
        loginWithEmailUseCase: LoginWithEmailUseCase,
        loginWithGoogleUseCase: LoginWithGoogleUseCase
    ): LoginViewModel = LoginViewModel(
        validateLoginInputUseCase, loginWithEmailUseCase, loginWithGoogleUseCase
    )
}