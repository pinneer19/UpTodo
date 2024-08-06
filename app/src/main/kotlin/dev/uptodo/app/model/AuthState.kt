package dev.uptodo.app.model

import dev.uptodo.domain.model.User

data class AuthState(val isAuthenticated: Boolean, val user: User?)