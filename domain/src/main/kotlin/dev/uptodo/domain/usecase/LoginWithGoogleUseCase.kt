package dev.uptodo.domain.usecase

import dev.uptodo.domain.repository.AccountService
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(
    private val accountService: AccountService
) {
    suspend operator fun invoke(idToken: String) {
        accountService.loginWithGoogle(idToken)
    }
}