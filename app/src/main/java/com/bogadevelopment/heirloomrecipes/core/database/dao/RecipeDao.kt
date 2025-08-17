package com.bogadevelopment.heirloomrecipes.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bogadevelopment.heirloomrecipes.core.database.entities.RecipesEntity

@Dao
interface RecipeDao {

    // SELECT


    @Query("SELECT * FROM recipe_table WHERE ownerProfileId = :profileId")
    suspend fun getRecipesForProfile(profileId: Int): List<RecipesEntity>

    @Query("SELECT * FROM recipe_table WHERE id = :id AND ownerProfileId = :profileId")
    suspend fun getRecipeByIdForProfile(id: Int, profileId: Int): RecipesEntity?


    // INSERT

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes : List<RecipesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: RecipesEntity)

    // DELETE

    @Query("DELETE FROM recipe_table WHERE id = :id AND ownerProfileId = :profileId")
    suspend fun deleteRecipeById(id: Int, profileId: Int)

    // UPDATE

    @Query("""
        UPDATE recipe_table 
        SET title = :title, ingredients = :ingredients, steps = :steps 
        WHERE id = :id AND ownerProfileId = :profileId
    """)
    suspend fun updateRecipeById(
        id: Int,
        profileId: Int,
        title: String,
        ingredients: String,
        steps: String
    )

}