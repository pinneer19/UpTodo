package dev.uptodo.app.di.main.category

import dagger.Subcomponent
import dev.uptodo.app.ui.screens.category.CategoryViewModel

@Subcomponent(modules = [CategoryModule::class])
@CategoryScope
interface CategoryComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): CategoryComponent
    }

    fun getViewModel(): CategoryViewModel
}