package dev.uptodo.data.di

import dagger.Binds
import dagger.Module
import dev.uptodo.data.repository.AccountServiceImpl
import dev.uptodo.data.repository.FirestoreTaskCategoryRepository
import dev.uptodo.data.repository.FirestoreTaskRepository
import dev.uptodo.domain.repository.AccountService
import dev.uptodo.domain.repository.RemoteTaskCategoryRepository
import dev.uptodo.domain.repository.RemoteTaskRepository

@Module
interface FirebaseBindModule {
    @Binds
    fun bindAccountService(service: AccountServiceImpl): AccountService

    @Binds
    fun bindTaskRepository(repository: FirestoreTaskRepository): RemoteTaskRepository

    @Binds
    fun bindTaskCategoryRepository(
        repository: FirestoreTaskCategoryRepository
    ): RemoteTaskCategoryRepository
}