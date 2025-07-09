package com.bogadevelopment.heirloomrecipes.features.recipes.ui

import androidx.lifecycle.ViewModel
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipesCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RecipesViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState : StateFlow<RecipesUiState> = _uiState

    fun onDialogConfirm(){
        addRecipe()
    }

    fun onDialogDismiss(){
        _uiState.update {
            it.copy(tittle = "", show = false)
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

    fun onTittleChanged(tittle: String) {
        _uiState.update { state ->
            state.copy(tittle = tittle)
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
        if(_uiState.value.tittle.isNotBlank()){
            _uiState.update {
                it.copy(recipes = it.recipes + RecipesCard(it.recipes.size, _uiState.value.tittle))
            }
        }
        onDialogDismiss()
    }

    private fun deleteRecipe(id : Int) {
        _uiState.update {
            it.copy(recipes = it.recipes.filter { it.id != id })
        }
    }
}

data class RecipesUiState(
    val tittle : String = "",
    val show : Boolean = false,
    val recipes : List<RecipesCard> = emptyList(),
    val expanded : Boolean = false
)