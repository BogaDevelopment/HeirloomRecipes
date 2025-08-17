package com.bogadevelopment.heirloomrecipes.features.recipes.domain

import com.bogadevelopment.heirloomrecipes.core.database.dao.RecipeDao
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipeRepository
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipesCard
import com.bogadevelopment.heirloomrecipes.features.recipes.data.toDomain
import com.bogadevelopment.heirloomrecipes.features.recipes.data.toEntity
import jakarta.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao
) : RecipeRepository {

    override suspend fun getRecipesByProfileId(profileId: Int): List<RecipesCard> =
        recipeDao.getRecipesForProfile(profileId).map { it.toDomain() }

    override suspend fun getRecipeById(id: Int, profileId: Int): RecipesCard? =
        recipeDao.getRecipeByIdForProfile(id, profileId)?.toDomain()

    override suspend fun insertAll(recipes: List<RecipesCard>) =
        recipeDao.insertAll(recipes.map { it.toEntity() })

    override suspend fun insert(recipe: RecipesCard) =
        recipeDao.insert(recipe.toEntity())

    override suspend fun deleteRecipeById(id: Int, profileId: Int) =
        recipeDao.deleteRecipeById(id, profileId)

    override suspend fun updateRecipeById(
        id: Int,
        profileId: Int,
        title: String,
        ingredients: String,
        steps: String
    ) = recipeDao.updateRecipeById(id = id, profileId = profileId, title =title, ingredients = ingredients, steps = steps)
}
