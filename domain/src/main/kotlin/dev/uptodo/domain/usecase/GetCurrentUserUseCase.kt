package dev.uptodo.domain.usecase

import dev.uptodo.domain.model.User
import dev.uptodo.domain.repository.AccountService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val accountService: AccountService
) {
    operator fun invoke(): Flow<User?> {
        return accountService.currentUser
    }
}