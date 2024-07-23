package dev.uptodo.domain.usecase

import dev.uptodo.domain.model.LoginInputValidationType
import dev.uptodo.domain.model.LoginInputValidationType.Valid
import dev.uptodo.domain.model.LoginInputValidationType.IncorrectEmail
import dev.uptodo.domain.model.LoginInputValidationType.EmptyField
import dev.uptodo.domain.util.isEmail

class ValidateLoginInputUseCase {
    operator fun invoke(email: String, password: String): LoginInputValidationType {
        if (email.isEmpty() || password.isEmpty()) {
            return EmptyField
        }
        if (!email.isEmail()) {
            return IncorrectEmail
        }
        return Valid
    }
}