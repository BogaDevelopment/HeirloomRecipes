package com.bogadevelopment.heirloomrecipes.features.login.domain

import com.bogadevelopment.heirloomrecipes.core.database.dao.ProfileDao
import com.bogadevelopment.heirloomrecipes.features.login.data.ProfileCard
import com.bogadevelopment.heirloomrecipes.features.login.data.ProfileRepository
import com.bogadevelopment.heirloomrecipes.features.login.data.toDomain
import com.bogadevelopment.heirloomrecipes.features.login.data.toEntity
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDao: ProfileDao
) : ProfileRepository {

    override suspend fun insertProfile(profile: ProfileCard) {
        profileDao.insertUser(profile.toEntity())
    }

    override suspend fun getProfileById(uid: String): ProfileCard? {
        return profileDao.getUserById(uid)?.toDomain()
    }
}