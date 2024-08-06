package dev.uptodo.app.di.auth.passwordReset

import dagger.Subcomponent
import dev.uptodo.app.ui.screens.passwordReset.PasswordResetViewModel

@Subcomponent(modules = [PasswordResetModule::class])
@PasswordResetScope
interface PasswordResetComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): PasswordResetComponent
    }

    fun getViewModel() : PasswordResetViewModel
}