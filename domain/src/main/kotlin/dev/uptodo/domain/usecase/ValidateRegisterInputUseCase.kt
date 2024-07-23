package dev.uptodo.domain.usecase

import dev.uptodo.domain.model.RegisterInputValidationType
import dev.uptodo.domain.model.RegisterInputValidationType.EmptyField
import dev.uptodo.domain.model.RegisterInputValidationType.PasswordsAreNotTheSame
import dev.uptodo.domain.model.RegisterInputValidationType.TooShortPassword
import dev.uptodo.domain.model.RegisterInputValidationType.PasswordLowerCaseMissing
import dev.uptodo.domain.model.RegisterInputValidationType.PasswordUpperCaseMissing
import dev.uptodo.domain.model.RegisterInputValidationType.PasswordSpecialCharacterMissing
import dev.uptodo.domain.model.RegisterInputValidationType.PasswordNumeralsMissing
import dev.uptodo.domain.model.RegisterInputValidationType.Valid
import dev.uptodo.domain.model.RegisterInputValidationType.IncorrectEmail
import dev.uptodo.domain.util.containsSpecialCharacter
import dev.uptodo.domain.util.containsThreeLowerCase
import dev.uptodo.domain.util.containsTwoNumerals
import dev.uptodo.domain.util.containsTwoUpperCase
import dev.uptodo.domain.util.isEmail

class ValidateRegisterInputUseCase {
    operator fun invoke(
        email: String,
        password: String,
        repeatedPassword: String
    ): RegisterInputValidationType {
        if (email.isEmpty() || password.isEmpty()) {
            return EmptyField
        }
        if (!email.isEmail()) {
            return IncorrectEmail
        }
        if (password != repeatedPassword) {
            return PasswordsAreNotTheSame
        }
        if (password.length < 8) {
            return TooShortPassword
        }
        if (!password.containsThreeLowerCase()) {
            return PasswordLowerCaseMissing
        }
        if (!password.containsTwoUpperCase()) {
            return PasswordUpperCaseMissing
        }
        if (!password.containsTwoNumerals()) {
            return PasswordNumeralsMissing
        }
        if (!password.containsSpecialCharacter()) {
            return PasswordSpecialCharacterMissing
        }
        return Valid
    }
}