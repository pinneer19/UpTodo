package dev.uptodo.app.di.main.task

import dagger.BindsInstance
import dagger.Subcomponent
import dev.uptodo.app.ui.screens.task.viewmodel.TaskViewModel
import dev.uptodo.domain.model.Task

@Subcomponent
@TaskScope
interface TaskComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun task(task: Task): Builder

        @BindsInstance
        fun taskId(id: String): Builder

        fun build(): TaskComponent
    }

    fun getViewModel(): TaskViewModel
}