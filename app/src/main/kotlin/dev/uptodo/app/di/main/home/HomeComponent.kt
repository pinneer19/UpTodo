package dev.uptodo.app.di.main.home

import dagger.Subcomponent
import dev.uptodo.app.ui.screens.home.viewmodel.HomeViewModel
import dev.uptodo.app.ui.screens.home.bottomsheet.viewmodel.TaskSheetViewModel

@Subcomponent
@HomeScope
interface HomeComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): HomeComponent
    }

    fun getHomeViewModel(): HomeViewModel

    fun getTaskSheetViewModel(): TaskSheetViewModel
}