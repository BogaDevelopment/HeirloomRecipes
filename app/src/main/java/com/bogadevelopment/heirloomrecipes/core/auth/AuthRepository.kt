package com.bogadevelopment.heirloomrecipes.core.auth

import com.bogadevelopment.heirloomrecipes.core.auth.model.UserSession

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): UserSession?
    suspend fun register(email: String, password: String): UserSession?
    fun getCurrentUser(): UserSession?
    fun logout()

    // 🔹 Nuevo método: escuchar el estado del usuario en tiempo real
    fun observeAuthState(): Flow<UserSession?>
}
