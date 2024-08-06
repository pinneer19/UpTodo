package dev.uptodo.domain.usecase

import dev.uptodo.domain.repository.AccountService
import javax.inject.Inject

class RegisterWithGoogleUseCase @Inject constructor(
    private val accountService: AccountService
) {
    suspend operator fun invoke(idToken: String) {
        accountService.registerWithGoogle(idToken)
    }
}