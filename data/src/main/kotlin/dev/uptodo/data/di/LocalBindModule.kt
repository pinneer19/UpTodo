package dev.uptodo.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dev.uptodo.data.local.dao.TaskCategoryDao
import dev.uptodo.data.repository.OfflineTaskCategoryRepositoryImpl
import dev.uptodo.data.repository.OfflineTaskRepositoryImpl
import dev.uptodo.domain.repository.OfflineTaskCategoryRepository
import dev.uptodo.domain.repository.OfflineTaskRepository

@Module
internal interface LocalBindModule {
    @Binds
    fun bindTaskRepository(repository: OfflineTaskRepositoryImpl): OfflineTaskRepository

    @Binds
    fun bindTaskCategoryRepository(
        repository: OfflineTaskCategoryRepositoryImpl
    ): OfflineTaskCategoryRepository
}