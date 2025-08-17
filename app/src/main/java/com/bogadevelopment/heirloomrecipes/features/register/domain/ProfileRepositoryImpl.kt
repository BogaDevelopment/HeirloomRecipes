package com.bogadevelopment.heirloomrecipes.features.register.domain

import com.bogadevelopment.heirloomrecipes.core.database.dao.ProfileDao
import com.bogadevelopment.heirloomrecipes.core.database.entities.ProfileWithRecipes
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipesCard
import com.bogadevelopment.heirloomrecipes.features.recipes.data.toEntity
import com.bogadevelopment.heirloomrecipes.features.register.data.ProfileData
import com.bogadevelopment.heirloomrecipes.features.register.data.ProfileRepository
import com.bogadevelopment.heirloomrecipes.features.register.data.toDomain
import com.bogadevelopment.heirloomrecipes.features.register.data.toEntity

class ProfileRepositoryImpl(
    private val profileDao: ProfileDao
) : ProfileRepository {

    override suspend fun insertProfile(profile: ProfileData): Long {
        return profileDao.insertProfile(profile.toEntity())
    }

    override suspend fun insertRecipe(recipe: RecipesCard) {
        profileDao.insertRecipe(recipe.toEntity())
    }

    override suspend fun getProfileWithRecipes(profileId: Int): ProfileWithRecipes {
        return profileDao.getProfileWithRecipes(profileId)
    }

    override suspend fun getProfileByFirebaseUid(uid: String): ProfileData? {
        return profileDao.getProfileByFirebaseUid(uid)?.toDomain()
    }

}
