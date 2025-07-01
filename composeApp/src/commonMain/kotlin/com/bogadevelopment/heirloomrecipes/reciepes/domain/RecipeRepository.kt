package com.bogadevelopment.heirloomrecipes.reciepes.domain

import com.bogadevelopment.heirloomrecipes.reciepes.data.RecipesCard
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getAllRecipes(): Flow<List<RecipesCard>>
    fun getById(id: Int): RecipesCard?
    fun addRecipe(tittle: String)
    fun deleteRecipe(id: Int)
}