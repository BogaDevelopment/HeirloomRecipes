package com.bogadevelopment.heirloomrecipes.recipe.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bogadevelopment.heirloomrecipes.database.Database
import com.bogadevelopment.heirloomrecipes.reciepes.data.RecipeRepositoryImpl
import com.bogadevelopment.heirloomrecipes.reciepes.data.RecipesCard

class RecipeViewModel(id : Int, private val db: Database)  : ViewModel(){

    var state by mutableStateOf<RecipesCard?>(null)
    private set

    init{
        state = RecipeRepositoryImpl(db).getById(id)
    }



}