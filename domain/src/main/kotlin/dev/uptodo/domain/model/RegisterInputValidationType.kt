package dev.uptodo.domain.model

enum class RegisterInputValidationType {
    EmptyField,
    IncorrectEmail,
    PasswordsAreNotTheSame,
    TooShortPassword,
    PasswordLowerCaseMissing,
    PasswordUpperCaseMissing,
    PasswordSpecialCharacterMissing,
    PasswordNumeralsMissing,
    Valid
}