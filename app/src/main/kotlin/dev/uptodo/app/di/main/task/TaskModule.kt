package dev.uptodo.app.di.main.task

import dagger.Module
import dagger.Provides
import dev.uptodo.app.ui.screens.task.TaskViewModel
import dev.uptodo.domain.model.Task
import dev.uptodo.domain.usecase.DeleteTaskUseCase
import dev.uptodo.domain.usecase.GetAllTaskCategoriesUseCase
import dev.uptodo.domain.usecase.GetTaskCategoryIdUseCase
import dev.uptodo.domain.usecase.UpdateTaskUseCase

@Module
class TaskModule {

    @Provides
    @TaskScope
    fun provideViewModel(
        getAllTaskCategoriesUseCase: GetAllTaskCategoriesUseCase,
        updateTaskUseCase: UpdateTaskUseCase,
        deleteTaskUseCase: DeleteTaskUseCase,
        getTaskCategoryIdUseCase: GetTaskCategoryIdUseCase,
        taskId: String,
        task: Task
    ): TaskViewModel =
        TaskViewModel(
            getAllTaskCategoriesUseCase,
            updateTaskUseCase,
            deleteTaskUseCase,
            getTaskCategoryIdUseCase,
            taskId,
            task
        )
}