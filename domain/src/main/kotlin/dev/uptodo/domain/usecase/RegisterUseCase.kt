package dev.uptodo.domain.usecase

import dev.uptodo.domain.repository.AccountService
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val accountService: AccountService
) {
    suspend operator fun invoke(email: String, password: String) {
        accountService.register(email, password)
    }
}