package com.bogadevelopment.heirloomrecipes.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bogadevelopment.heirloomrecipes.core.database.entities.RecipesEntity

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe_table")
    suspend fun getAllRecipes(): List<RecipesEntity>

    @Query("SELECT * FROM recipe_table WHERE id = :id")
    suspend fun getRecipeById(id: Int): RecipesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes : List<RecipesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: RecipesEntity)

    @Delete
    suspend fun delete(recipe: RecipesEntity)

}