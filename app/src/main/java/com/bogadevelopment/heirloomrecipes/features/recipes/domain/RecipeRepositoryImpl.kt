package com.bogadevelopment.heirloomrecipes.features.recipes.domain

import com.bogadevelopment.heirloomrecipes.core.database.dao.RecipeDao
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipeRepository
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipesCard
import com.bogadevelopment.heirloomrecipes.features.recipes.data.toDomain
import com.bogadevelopment.heirloomrecipes.features.recipes.data.toEntity
import jakarta.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao : RecipeDao
) : RecipeRepository{

    override suspend fun getAllRecipes(): List<RecipesCard> = recipeDao.getAllRecipes().map { it.toDomain() }

    override suspend fun getRecipeById(id: Int): RecipesCard? = recipeDao.getRecipeById(id)?.toDomain()

    override suspend fun insertAll(recipes: List<RecipesCard>) = recipeDao.insertAll(recipes.map { it.toEntity() })

    override suspend fun insert(recipe: RecipesCard) = recipeDao.insert(recipe.toEntity())

    override suspend fun deleteRecipe(recipe: RecipesCard) = recipeDao.delete(recipe.toEntity())
}