package com.bogadevelopment.heirloomrecipes.features.recipes.data


interface RecipeRepository {

    suspend fun getRecipesByProfileId(profileId: Int): List<RecipesCard>

    suspend fun getRecipeById(id: Int, profileId: Int): RecipesCard?

    suspend fun insertAll(recipes: List<RecipesCard>)

    suspend fun insert(recipe: RecipesCard)

    suspend fun deleteRecipeById(id: Int, profileId: Int)

    suspend fun updateRecipeById(id: Int, profileId: Int, title: String, ingredients: String, steps: String)


}