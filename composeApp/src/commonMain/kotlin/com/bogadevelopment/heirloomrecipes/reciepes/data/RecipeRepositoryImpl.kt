package com.bogadevelopment.heirloomrecipes.reciepes.data

import com.bogadevelopment.heirloomrecipes.database.Database
import com.bogadevelopment.heirloomrecipes.reciepes.domain.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow



class RecipeRepositoryImpl(private val db: Database) : RecipeRepository {

    private val _recipesFlow = MutableStateFlow<List<RecipesCard>>(emptyList())

    init{
        refreshRecipes()
    }


    override fun getAllRecipes(): Flow<List<RecipesCard>> = _recipesFlow


    private fun refreshRecipes() {
        val list = db.recipesDBQueries.selectAll().executeAsList()
            .map { RecipesCard(id = it.id.toInt(), tittle = it.tittle) }
        _recipesFlow.value = list
    }

    override fun getById(id: Int): RecipesCard? {
        return db.recipesDBQueries.selectById(id.toLong()).executeAsOneOrNull()?.let {
            RecipesCard(id = it.id.toInt(), tittle = it.tittle)
        }
    }

    override fun addRecipe(tittle: String) {
        db.recipesDBQueries.insert(tittle)
        refreshRecipes()
    }

    override fun deleteRecipe(id: Int) {
        db.recipesDBQueries.deleteById(id.toLong())
        refreshRecipes()
    }
}