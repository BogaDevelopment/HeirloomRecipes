package com.bogadevelopment.heirloomrecipes.reciepes.data

import androidx.compose.runtime.mutableStateListOf

data class RecipesCard (
    val id : Int,
    val tittle: String
)

object RecipeRepository{
    private val _recipes = mutableStateListOf<RecipesCard>()
    val recipes : List<RecipesCard> get() = _recipes


    fun addRecipe(text : String){
        val newId = (_recipes.maxOfOrNull{ it.id} ?: 0) + 1
        _recipes.add(RecipesCard(id = newId, tittle = text))
    }

    fun getById(id: Int): RecipesCard? {
        return _recipes.find { it.id == id }
    }

    fun deleteRecipe(id: Int) {
        _recipes.removeAll { it.id == id }
    }

}

