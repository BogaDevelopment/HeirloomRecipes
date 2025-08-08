package com.bogadevelopment.heirloomrecipes.features.recipes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipeRepository
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipesCard
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RecipesViewModel @Inject constructor(
        private val recipeRepo : RecipeRepository
    ): ViewModel(){

    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState : StateFlow<RecipesUiState> = _uiState

    init{
        loadRecipes()
    }

    fun onDialogConfirm(){
        addRecipe()
    }

    fun onDialogDismiss(){
        _uiState.update {
            it.copy(title = "", show = false)
        }
    }

    fun openDialog(){
        _uiState.update {
            it.copy(show = true)
        }
    }

    fun deleteIdRecipe(id : Int){
        deleteRecipe(id)
    }

    fun onTittleChanged(title: String) {
        _uiState.update { state ->
            state.copy(title = title)
        }
    }

    fun onExpandedChanged(id : Int) {
        _uiState.update { state ->
            if (state.expandedId == id) {
                state.copy(expandedId = null) // si ya estaba abierto, lo cerramos
            } else {
                state.copy(expandedId = id) // abrimos el dropdown para ese id
            }
        }
    }

    fun onExpandedDismiss(){
        _uiState.update { it.copy(expandedId = null) }
    }

    fun logout() {
        try
        {
            Firebase.auth.signOut()
        }
        catch (e : Exception) {
            println(e.message)
        }
    }

    private fun addRecipe(){
        val title = _uiState.value.title
        if(title.isNotBlank()){
            val newRecipe = RecipesCard(0, title)
            viewModelScope.launch {
                recipeRepo.insert(newRecipe)
                loadRecipes()
            }
        }
        onDialogDismiss()
    }

    private fun deleteRecipe(id : Int) {
        viewModelScope.launch {
            recipeRepo.deleteRecipeById(id)
            loadRecipes()
        }
    }

    private fun loadRecipes(){
        viewModelScope.launch {
            try{
                _uiState.update {
                    it.copy(recipes = recipeRepo.getAllRecipes())
                }
            }catch (e : Exception){
                println(e.message)
            }

        }
    }

}

data class RecipesUiState(
    val title : String = "",
    val show : Boolean = false,
    val recipes : List<RecipesCard> = emptyList(),
    val expandedId : Int? = null
)