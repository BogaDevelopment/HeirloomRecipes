package com.bogadevelopment.heirloomrecipes.features.recipe.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RecipeViewModel(val id: Int)  : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState


    fun onExpandedChanged(type : String){
        _uiState.update { state ->
            if(type == "ingredients"){
                state.copy(ingredientsExpanded = !_uiState.value.ingredientsExpanded)
            }else{
                state.copy(stepsExpanded = !_uiState.value.stepsExpanded)
            }
        }
    }

    fun onIngredientChanged(ingredients : String){
        _uiState.update {
            it.copy(ingredients = ingredients)
        }
    }

    fun onStepsChanged(steps : String){
        _uiState.update {
            it.copy(steps = steps)
        }
    }

}

data class RecipeUiState(
    val tittle: String = "",
    val ingredients: String = "",
    val steps: String = "",
    val ingredientsExpanded : Boolean = false,
    val stepsExpanded : Boolean = false
)
