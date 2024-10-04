package dev.uptodo.app.di.auth.register

import dagger.Subcomponent
import dev.uptodo.app.ui.screens.register.viewmodel.RegisterViewModel

@Subcomponent
@RegisterScope
interface RegisterComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): RegisterComponent
    }

    fun getViewModel() : RegisterViewModel
}