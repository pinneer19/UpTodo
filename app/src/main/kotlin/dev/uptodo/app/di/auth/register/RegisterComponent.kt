package dev.uptodo.app.di.auth.register

import dagger.Subcomponent
import dev.uptodo.app.ui.screens.register.RegisterViewModel

@Subcomponent(modules = [RegisterModule::class])
@RegisterScope
interface RegisterComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): RegisterComponent
    }

    fun getViewModel() : RegisterViewModel
}