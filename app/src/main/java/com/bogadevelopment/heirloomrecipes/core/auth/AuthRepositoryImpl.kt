package com.bogadevelopment.heirloomrecipes.core.auth

import com.bogadevelopment.heirloomrecipes.core.auth.datasource.FirebaseAuthDataSource
import com.bogadevelopment.heirloomrecipes.core.auth.model.UserSession

class AuthRepositoryImpl(
    private val firebaseAuthDataSource: FirebaseAuthDataSource
) : AuthRepository {

    override suspend fun login(email: String, password: String): UserSession? {
        return firebaseAuthDataSource.login(email, password)
    }

    override suspend fun register(email: String, password: String): UserSession? {
        return firebaseAuthDataSource.register(email, password)
    }

    override fun getCurrentUser(): UserSession? {
        return firebaseAuthDataSource.getCurrentUser()
    }

    override fun logout() = firebaseAuthDataSource.logout()
}