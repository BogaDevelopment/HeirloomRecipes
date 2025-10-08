package com.bogadevelopment.heirloomrecipes.core.auth

import com.bogadevelopment.heirloomrecipes.core.auth.model.UserSession

interface AuthRepository {

    suspend fun login(email: String, password: String): UserSession?
    suspend fun register(email: String, password: String): UserSession?
    fun getCurrentUser(): UserSession?
    fun logout()
}