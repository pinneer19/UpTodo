package dev.uptodo.app.di.main.category

import dagger.BindsInstance
import dagger.Subcomponent
import dev.uptodo.app.ui.screens.category.viewmodel.CategoryViewModel
import dev.uptodo.domain.model.TaskCategory

@Subcomponent
@CategoryScope
interface CategoryComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun categoryId(id: String?): Builder

        @BindsInstance
        fun category(category: TaskCategory?): Builder

        fun build(): CategoryComponent
    }

    fun getViewModel(): CategoryViewModel
}