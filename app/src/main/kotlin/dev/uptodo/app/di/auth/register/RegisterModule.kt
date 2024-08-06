package dev.uptodo.app.di.auth.register

import dagger.Module
import dagger.Provides
import dev.uptodo.app.ui.screens.register.RegisterViewModel
import dev.uptodo.domain.usecase.RegisterWithEmailUseCase
import dev.uptodo.domain.usecase.RegisterWithGoogleUseCase
import dev.uptodo.domain.usecase.ValidateRegisterInputUseCase

@Module
class RegisterModule {

    @Provides
    @RegisterScope
    fun provideViewModel(
        validateRegisterInputUseCase: ValidateRegisterInputUseCase,
        registerWithEmailUseCase: RegisterWithEmailUseCase,
        registerWithGoogleUseCase: RegisterWithGoogleUseCase
    ): RegisterViewModel = RegisterViewModel(
        validateRegisterInputUseCase, registerWithEmailUseCase, registerWithGoogleUseCase
    )
}