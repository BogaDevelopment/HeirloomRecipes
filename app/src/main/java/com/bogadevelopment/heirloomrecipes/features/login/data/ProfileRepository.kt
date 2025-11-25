package com.bogadevelopment.heirloomrecipes.features.login.data

import com.bogadevelopment.heirloomrecipes.core.database.entities.ProfileEntity

interface ProfileRepository {
    suspend fun insertProfile(profile: ProfileCard)
    suspend fun getProfileById(uid: String): ProfileCard?
}