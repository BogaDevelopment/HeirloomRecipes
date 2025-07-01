package com.bogadevelopment.heirloomrecipes.reciepes.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogadevelopment.heirloomrecipes.database.Database
import com.bogadevelopment.heirloomrecipes.reciepes.data.RecipeRepositoryImpl
import com.bogadevelopment.heirloomrecipes.reciepes.data.RecipesCard
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class RecipesViewModel(database: Database): ViewModel(){

    private val repo = RecipeRepositoryImpl(database)

    var show by mutableStateOf(false)
        private set
    var text by mutableStateOf("")

    var recipes : SnapshotStateList<RecipesCard> = mutableStateListOf()
        private set

    init{
        viewModelScope.launch {
            repo.getAllRecipes().collect{
                recipes.clear()
                recipes.addAll(it)
            }
        }
    }

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
            viewModelScope.launch {
                repo.addRecipe(text)
            }
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