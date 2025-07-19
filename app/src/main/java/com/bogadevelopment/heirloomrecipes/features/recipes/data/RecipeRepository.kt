package com.bogadevelopment.heirloomrecipes.features.recipes.data


interface RecipeRepository {

    suspend fun getAllRecipes() : List<RecipesCard>
    suspend fun getRecipeById(id : Int) : RecipesCard?
    suspend fun insertAll(recipes : List<RecipesCard>)
    suspend fun insert(recipe: RecipesCard)
    suspend fun deleteRecipeById(id:Int)


}