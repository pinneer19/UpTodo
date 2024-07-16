package dev.uptodo.domain

import dev.uptodo.domain.model.LoginInputValidationType
import dev.uptodo.domain.usecase.ValidateLoginInputUseCase
import org.junit.Assert
import org.junit.Test

class ValidateLoginInputUseCaseTest {
    val validator = ValidateLoginInputUseCase()

    @Test
    fun `returns EmptyField if email is empty`() {
        val (email, password) = "" to "password"
        val validateResult = validator(email, password)
        val isEmptyField = validateResult == LoginInputValidationType.EmptyField

        Assert.assertTrue(isEmptyField)
    }

    @Test
    fun `returns EmptyField if password is empty`() {
        val (email, password) = "email" to ""
        val validateResult = validator(email, password)
        val isEmptyField = validateResult == LoginInputValidationType.EmptyField

        Assert.assertTrue(isEmptyField)
    }

    @Test
    fun `returns IncorrectEmail if email has wrong structure`() {
        val (email, password) = "wrong@email" to "password"
        val validateResult = validator(email, password)
        val isIncorrectEmail = validateResult == LoginInputValidationType.IncorrectEmail

        Assert.assertTrue(isIncorrectEmail)
    }

    @Test
    fun `returns Valid if both email and password have correct form`() {
        val (email, password) = "example@email.com" to "password"
        val validateResult = validator(email, password)
        val isValid = validateResult == LoginInputValidationType.Valid

        Assert.assertTrue(isValid)
    }
}