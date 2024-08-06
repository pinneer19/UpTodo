package dev.uptodo.domain.usecase

import dev.uptodo.domain.repository.AccountService
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val accountService: AccountService
) {
    suspend operator fun invoke(email: String) {
        accountService.resetPassword(email)
    }
}