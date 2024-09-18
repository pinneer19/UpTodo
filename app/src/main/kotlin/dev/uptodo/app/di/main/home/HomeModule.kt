package dev.uptodo.app.di.main.home

import dagger.Module
import dagger.Provides
import dev.uptodo.app.ui.screens.home.HomeViewModel
import dev.uptodo.domain.usecase.CreateTaskUseCase
import dev.uptodo.domain.usecase.DeleteTaskUseCase
import dev.uptodo.domain.usecase.GetCurrentDateTasksUseCase
import dev.uptodo.domain.usecase.GetTaskCategoriesFlowUseCase
import dev.uptodo.domain.usecase.UpdateTaskCompleteStateUseCase

@Module
class HomeModule {

    @Provides
    @HomeScope
    fun provideHomeViewModel(
        getCurrentDateTasksUseCase: GetCurrentDateTasksUseCase,
        getTaskCategoriesFlowUseCase: GetTaskCategoriesFlowUseCase,
        createTaskUseCase: CreateTaskUseCase,
        updateTaskCompleteStateUseCase: UpdateTaskCompleteStateUseCase,
        deleteTaskUseCase: DeleteTaskUseCase
    ): HomeViewModel = HomeViewModel(
        getCurrentDateTasksUseCase,
        getTaskCategoriesFlowUseCase,
        createTaskUseCase,
        updateTaskCompleteStateUseCase,
        deleteTaskUseCase
    )
}