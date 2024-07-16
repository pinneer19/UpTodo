package dev.uptodo.domain

import dev.uptodo.domain.model.RegisterInputValidationType
import dev.uptodo.domain.usecase.ValidateRegisterInputUseCase
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidateRegisterInputUseCaseTest {
    val validator = ValidateRegisterInputUseCase()

    @Test
    fun `returns EmptyField if email is empty`() {
        val (email, password, repeatedPassword) = Triple("", "password", "password")
        val validateResult = validator(email, password, repeatedPassword)
        val isEmptyField = validateResult == RegisterInputValidationType.EmptyField

        assertTrue(isEmptyField)
    }

    @Test
    fun `returns EmptyField if password is empty`() {
        val (email, password, repeatedPassword) = Triple("email", "", "")

        val validateResult = validator(email, password, repeatedPassword)
        val isEmptyField = validateResult == RegisterInputValidationType.EmptyField
        assertTrue(isEmptyField)
    }

    @Test
    fun `returns IncorrectEmail if email has wrong structure`() {
        val (email, password, repeatedPassword) = Triple("wrong@email", "password", "password")
        val validateResult = validator(email, password, repeatedPassword)
        val isIncorrectEmail = validateResult == RegisterInputValidationType.IncorrectEmail

        assertTrue(isIncorrectEmail)
    }

    @Test
    fun `returns PasswordsAreNotTheSame if repeated password doesn't match`() {
        val (email, password, repeatedPassword) = Triple(
            "example@email.com",
            "password",
            "another_one"
        )
        val validateResult = validator(email, password, repeatedPassword)
        val arePasswordsNotTheSame =
            validateResult == RegisterInputValidationType.PasswordsAreNotTheSame

        assertTrue(arePasswordsNotTheSame)
    }

    @Test
    fun `returns TooShortPassword if password length less than 8`() {
        val (email, password, repeatedPassword) = Triple("example@email.com", "eight", "eight")
        val validateResult = validator(email, password, repeatedPassword)
        val isTooShortPassword = validateResult == RegisterInputValidationType.TooShortPassword

        assertTrue(isTooShortPassword)
    }

    @Test
    fun `returns PasswordLowerCaseMissing if password has less than 3 lowercase chars`() {
        val (email, password, repeatedPassword) = Triple(
            "example@email.com",
            "PASS wORD Ab",
            "PASS wORD Ab"
        )
        val validateResult = validator(email, password, repeatedPassword)
        val isPasswordLowerCaseMissing =
            validateResult == RegisterInputValidationType.PasswordLowerCaseMissing

        assertTrue(isPasswordLowerCaseMissing)
    }

    @Test
    fun `returns PasswordUpperCaseMissing if password has less than 2 uppercase chars`() {
        val (email, password, repeatedPassword) = Triple(
            "example@email.com",
            "pass Word",
            "pass Word"
        )
        val validateResult = validator(email, password, repeatedPassword)
        val isPasswordUpperCaseMissing =
            validateResult == RegisterInputValidationType.PasswordUpperCaseMissing

        assertTrue(isPasswordUpperCaseMissing)
    }

    @Test
    fun `returns PasswordNumeralsMissing if password length less than 2 numerals`() {
        val (email, password, repeatedPassword) = Triple(
            "example@email.com",
            "password 4 AB",
            "password 4 AB"
        )
        val validateResult = validator(email, password, repeatedPassword)
        val isPasswordNumeralsMissing =
            validateResult == RegisterInputValidationType.PasswordNumeralsMissing

        assertTrue(isPasswordNumeralsMissing)
    }

    @Test
    fun `returns PasswordSpecialCharacterMissing if password doesn't contain special char`() {
        val (email, password, repeatedPassword) = Triple(
            "example@email.com",
            "passw1O2R3d",
            "passw1O2R3d"
        )
        val validateResult = validator(email, password, repeatedPassword)
        val isPasswordSpecialCharacterMissing =
            validateResult == RegisterInputValidationType.PasswordSpecialCharacterMissing

        assertTrue(isPasswordSpecialCharacterMissing)
    }

    @Test
    fun `returns Valid if both email and password have correct form`() {
        val (email, password, repeatedPassword) = Triple(
            "example@email.com",
            "password_AB_123_$",
            "password_AB_123_$"
        )
        val validateResult = validator(email, password, repeatedPassword)
        val isValid = validateResult == RegisterInputValidationType.Valid

        assertTrue(isValid)
    }
}