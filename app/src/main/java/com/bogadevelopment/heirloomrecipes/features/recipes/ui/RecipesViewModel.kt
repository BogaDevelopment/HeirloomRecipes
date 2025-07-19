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
        private val repository : RecipeRepository
    ): ViewModel(){

    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState : StateFlow<RecipesUiState> = _uiState

    init{
        loadRecipes()
    }

    private fun loadRecipes(){
        viewModelScope.launch {
            try{
                _uiState.update {
                    it.copy(recipes = repository.getAllRecipes())
                }
            }catch (e : Exception){
                println(e.message)
            }

        }
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

    fun onExpandedChanged() {
        _uiState.update { state ->
            state.copy(expanded = !state.expanded)
        }
    }

    fun onExpandedDismiss(){
        _uiState.update { state ->
            state.copy(expanded = false)
        }
    }

    private fun addRecipe(){
        val title = _uiState.value.title
        if(title.isNotBlank()){
            val newRecipe = RecipesCard(0, title)
            viewModelScope.launch {
                repository.insert(newRecipe)
                loadRecipes()
            }
        }
        onDialogDismiss()
    }

    private fun deleteRecipe(id : Int) {
        _uiState.update { it ->
            it.copy(recipes = it.recipes.filter { it.id != id })
        }
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

}

data class RecipesUiState(
    val title : String = "",
    val show : Boolean = false,
    val recipes : List<RecipesCard> = emptyList(),
    val expanded : Boolean = false
)