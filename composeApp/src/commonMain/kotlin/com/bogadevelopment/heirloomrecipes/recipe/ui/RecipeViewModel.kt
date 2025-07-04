package com.bogadevelopment.heirloomrecipes.recipe.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bogadevelopment.heirloomrecipes.database.Database
import com.bogadevelopment.heirloomrecipes.reciepes.data.RecipeRepositoryImpl
import com.bogadevelopment.heirloomrecipes.reciepes.data.RecipesCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RecipeViewModel(id : Int, private val db: Database)  : ViewModel(){

    var state by mutableStateOf<RecipesCard?>(null)
    private set

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState

    init{
        state = RecipeRepositoryImpl(db).getById(id)
    }

    fun onIngredientChanged(ingredients: String){
        _uiState.update { state ->
            state.copy(ingredients = ingredients)
        }
    }

    fun onStepsChanged(steps: String){
        _uiState.update { state ->
            state.copy(steps = steps)
        }
    }

    fun onExpandedChanged(type : String){
        _uiState.update { state ->
            if(type == "ingredients"){
                state.copy(ingredientsExpanded = !_uiState.value.ingredientsExpanded)
            }else{
                state.copy(stepsExpanded = !_uiState.value.stepsExpanded)
            }
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