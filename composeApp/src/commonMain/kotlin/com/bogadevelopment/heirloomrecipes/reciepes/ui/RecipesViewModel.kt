package com.bogadevelopment.heirloomrecipes.reciepes.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bogadevelopment.heirloomrecipes.reciepes.data.RecipeRepository
import com.bogadevelopment.heirloomrecipes.reciepes.data.RecipesCard

class RecipesViewModel : ViewModel(){

    var show by mutableStateOf(false)
        private set
    var text by mutableStateOf("")

    val recipes : List<RecipesCard> get() = RecipeRepository.recipes

    fun onDialogConfirm(){
        addRecipe()
    }

    fun onDialogDismiss(){
        text = ""
        show = false
    }

    fun openDialog(){
        show = true
    }

    fun addRecipe(){
        if(text.isNotBlank()){
            RecipeRepository.addRecipe(text)
        }
        onDialogDismiss()
    }

    fun getRecipeById(id : Int){
        RecipeRepository.getById(id)
    }

}