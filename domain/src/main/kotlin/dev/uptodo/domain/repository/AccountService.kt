package dev.uptodo.domain.repository

import dev.uptodo.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUser: Flow<User?>
    val currentUserId: String
    fun hasUser(): Boolean
    suspend fun login(email: String, password: String)
    suspend fun register(email: String, password: String)
    suspend fun logout()
    suspend fun deleteAccount()
}