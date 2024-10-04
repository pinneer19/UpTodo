package dev.uptodo.app.di.auth.login

import dagger.Subcomponent
import dev.uptodo.app.ui.screens.login.viewmodel.LoginViewModel

@Subcomponent
@LoginScope
interface LoginComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): LoginComponent
    }

    fun getViewModel() : LoginViewModel
}