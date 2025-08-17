package com.bogadevelopment.heirloomrecipes.features.register.data

import com.bogadevelopment.heirloomrecipes.core.database.entities.ProfileWithRecipes
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipesCard

interface ProfileRepository {
    suspend fun insertProfile(profile: ProfileData): Long
    suspend fun insertRecipe(recipe: RecipesCard)
    suspend fun getProfileWithRecipes(profileId: Int): ProfileWithRecipes
    suspend fun getProfileByFirebaseUid(uid: String): ProfileData?
}