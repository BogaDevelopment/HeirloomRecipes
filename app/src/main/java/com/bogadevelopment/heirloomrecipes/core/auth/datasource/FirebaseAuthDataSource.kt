package com.bogadevelopment.heirloomrecipes.core.auth.datasource

import com.bogadevelopment.heirloomrecipes.core.auth.model.UserSession
import com.bogadevelopment.heirloomrecipes.core.auth.service.FirebaseAuthService

class FirebaseAuthDataSource(
    private val firebaseAuthService: FirebaseAuthService
) {

    suspend fun login(email: String, password: String): UserSession? {
        val user = firebaseAuthService.signIn(email, password)
        return user?.let { UserSession(it.uid, it.email) }
    }

    suspend fun register(email: String, password: String): UserSession? {
        val user = firebaseAuthService.signUp(email, password)
        return user?.let { UserSession(it.uid, it.email) }
    }

    fun getCurrentUser(): UserSession? {
        val user = firebaseAuthService.getCurrentUser()
        return user?.let { UserSession(it.uid, it.email) }
    }

    fun logout() = firebaseAuthService.signOut()
}