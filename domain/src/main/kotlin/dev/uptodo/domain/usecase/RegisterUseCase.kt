package dev.uptodo.domain.usecase

import dev.uptodo.domain.repository.AccountService
import javax.inject.Inject

class RegisterWithEmailUseCase @Inject constructor(
    private val accountService: AccountService
) {
    suspend operator fun invoke(email: String, password: String) {
        accountService.registerWithEmail(email, password)
    }
}