package com.bogadevelopment.heirloomrecipes.features.recipes.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogadevelopment.heirloomrecipes.core.auth.AuthRepository
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipeRepository
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipesCard
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RecipesViewModel @Inject constructor(
        private val recipeRepo : RecipeRepository,
        private val authRepository: AuthRepository
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
        try {
            authRepository.logout()
        } catch (e : Exception) {
            println(e.message)
        }
    }


    private fun addRecipe() {
        val title = _uiState.value.title
        if (title.isNotBlank()) {
            viewModelScope.launch {
                try {
                    // Get the current user from the authrepository
                    val user = authRepository.getCurrentUser()

                    if (user == null) {
                        Log.e("RECIPES", "No hay usuario logueado, no se puede agregar receta")
                        return@launch
                    }

                    // Create a new recipe with the user's UID
                    val newRecipe = RecipesCard(
                        id = 0,
                        title = title,
                        ingredients = "",
                        steps = "",
                        userUid = user.uid
                    )

                    // Insert in the Local db
                    recipeRepo.insert(newRecipe)

                    // refresh list
                    loadRecipes()

                } catch (e: Exception) {
                    Log.e("RECIPES", "❌ Error al agregar receta: ${e.message}")
                }
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

    private fun loadRecipes() {
        viewModelScope.launch {
            try {
                val user = authRepository.getCurrentUser()
                if (user != null) {
                    val userRecipes = recipeRepo.getRecipesByUser(user.uid)
                    _uiState.update { it.copy(recipes = userRecipes) }
                } else {
                    _uiState.update { it.copy(recipes = emptyList()) }
                }
            } catch (e: Exception) {
                println("❌ Error al cargar recetas: ${e.message}")
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