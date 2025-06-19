package com.bogadevelopment.heirloomrecipes.reciepes.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bogadevelopment.heirloomrecipes.reciepes.data.RecipesCard

class RecipesViewModel : ViewModel(){

    var show by mutableStateOf(false)
        private set
    var text by mutableStateOf("")

    private val _recipes = mutableStateListOf<RecipesCard>()
    val recipe : List<RecipesCard> get() = _recipes


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
            val newId = (_recipes.maxOfOrNull{ it.id} ?: 0) + 1
            _recipes.add(RecipesCard(id = newId, tittle = text))
        }
        onDialogDismiss()
    }

}