package com.bogadevelopment.heirloomrecipes.core.auth

import com.bogadevelopment.heirloomrecipes.features.register.data.ProfileData
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val currentUser: Flow<ProfileData?>
    suspend fun login(email: String, password: String): Result<ProfileData>
    suspend fun register(email: String, password: String, name: String, lastName: String): Result<ProfileData>
    suspend fun logout()

}