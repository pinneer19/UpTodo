package dev.uptodo.app.di.main.home

import dagger.Module
import dagger.Provides
import dev.uptodo.app.ui.screens.home.HomeViewModel
import dev.uptodo.domain.usecase.GetTasksUseCase

@Module
class HomeModule {

    @Provides
    @HomeScope
    fun provideHomeViewModel(
        getTasksUseCase: GetTasksUseCase
    ): HomeViewModel = HomeViewModel(getTasksUseCase)
}