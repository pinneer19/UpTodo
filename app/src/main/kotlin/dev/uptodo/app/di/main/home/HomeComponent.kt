package dev.uptodo.app.di.main.home

import dagger.Subcomponent
import dev.uptodo.app.ui.screens.home.HomeViewModel

@Subcomponent(modules = [HomeModule::class])
@HomeScope
interface HomeComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): HomeComponent
    }

    fun getViewModel(): HomeViewModel
}