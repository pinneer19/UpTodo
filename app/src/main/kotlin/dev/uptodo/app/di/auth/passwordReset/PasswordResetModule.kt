package dev.uptodo.app.di.auth.passwordReset

import dagger.Module
import dagger.Provides
import dev.uptodo.app.ui.screens.passwordReset.PasswordResetViewModel
import dev.uptodo.domain.usecase.ResetPasswordUseCase

@Module
class PasswordResetModule {

    @Provides
    @PasswordResetScope
    fun provideViewModel(
        resetPasswordUseCase: ResetPasswordUseCase
    ): PasswordResetViewModel = PasswordResetViewModel(resetPasswordUseCase)
}