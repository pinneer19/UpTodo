package dev.uptodo.domain.repository

import dev.uptodo.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {

    val currentUser: Flow<User?>

    val currentUserId: String

    fun hasUser(): Boolean

    suspend fun resetPassword(email: String)

    suspend fun loginWithEmail(email: String, password: String)

    suspend fun loginWithGoogle(idToken: String)

    suspend fun registerWithEmail(email: String, password: String)

    suspend fun registerWithGoogle(idToken: String)

    suspend fun logout()

    suspend fun deleteAccount()
}