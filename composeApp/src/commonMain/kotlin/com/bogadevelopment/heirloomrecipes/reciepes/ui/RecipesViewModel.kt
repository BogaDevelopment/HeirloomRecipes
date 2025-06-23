package com.bogadevelopment.heirloomrecipes.reciepes.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bogadevelopment.heirloomrecipes.reciepes.data.RecipeRepository
import com.bogadevelopment.heirloomrecipes.reciepes.data.RecipesCard
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

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

    private fun addRecipe(){
        if(text.isNotBlank()){
            RecipeRepository.addRecipe(text)
        }
        onDialogDismiss()
    }

    suspend fun logout() {
        try
        {
            Firebase.auth.signOut()
        }
        catch (e : Exception) {
            println(e.message)
        }
    }

    /* To do

    fun getRecipeById(id : Int){
        RecipeRepository.getById(id)
    }
     */
}