package dev.uptodo.app.di.main.category

import dagger.Module
import dagger.Provides
import dev.uptodo.app.ui.screens.category.CategoryViewModel
import dev.uptodo.domain.usecase.CreateTaskCategoryUseCase
import dev.uptodo.domain.usecase.GetIconNamesUseCase

@Module
class CategoryModule {

    @Provides
    @CategoryScope
    fun provideCategoryViewModel(
        getIconNamesUseCase: GetIconNamesUseCase,
        createTaskCategoryUseCase: CreateTaskCategoryUseCase
    ): CategoryViewModel =
        CategoryViewModel(getIconNamesUseCase, createTaskCategoryUseCase)
}