package com.bogadevelopment.heirloomrecipes.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.bogadevelopment.heirloomrecipes.core.database.entities.ProfileEntity
import com.bogadevelopment.heirloomrecipes.core.database.entities.ProfileWithRecipes
import com.bogadevelopment.heirloomrecipes.core.database.entities.RecipesEntity

@Dao
interface ProfileDao {
    @Insert
    suspend fun insertProfile(profile: ProfileEntity): Long

    @Insert
    suspend fun insertRecipe(recipe: RecipesEntity)

    @Transaction
    @Query("SELECT * FROM profile_table WHERE profile_id = :id")
    suspend fun getProfileWithRecipes(id: Int): ProfileWithRecipes

    @Query("SELECT * FROM profile_table WHERE firebase_uid = :uid LIMIT 1")
    suspend fun getProfileByFirebaseUid(uid: String): ProfileEntity?
}