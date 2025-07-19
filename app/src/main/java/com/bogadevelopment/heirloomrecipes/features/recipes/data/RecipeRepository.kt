package com.bogadevelopment.heirloomrecipes.features.recipes.data

import com.bogadevelopment.heirloomrecipes.core.database.entities.RecipesEntity

interface RecipeRepository {

    suspend fun getAllRecipes() : List<RecipesCard>
    suspend fun getRecipeById(id : Int) : RecipesCard?
    suspend fun insertAll(recipes : List<RecipesCard>)
    suspend fun insert(recipe: RecipesCard)
    suspend fun deleteRecipe(recipe : RecipesCard)


}