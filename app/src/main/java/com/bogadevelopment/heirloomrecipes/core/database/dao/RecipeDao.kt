package com.bogadevelopment.heirloomrecipes.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bogadevelopment.heirloomrecipes.core.database.entities.RecipesEntity

@Dao
interface RecipeDao {

    // SELECT

    @Query("SELECT * FROM recipe_table")
    suspend fun getAllRecipes(): List<RecipesEntity>

    @Query("SELECT * FROM recipe_table WHERE id = :id")
    suspend fun getRecipeById(id: Int): RecipesEntity?

    @Query("SELECT * FROM recipe_table WHERE user_uid = :uid")
    suspend fun getRecipesByUser(uid: String): List<RecipesEntity>

    // INSERT

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes : List<RecipesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: RecipesEntity)

    // DELETE

    @Query("DELETE FROM recipe_table WHERE id = :id")
    suspend fun deleteById(id: Int)

    // UPDATE

    @Query("UPDATE recipe_table SET title = :title, ingredients = :ingredients, steps = :steps WHERE id = :id")
    suspend fun updateRecipeById(id: Int, title: String, ingredients: String, steps: String)

}