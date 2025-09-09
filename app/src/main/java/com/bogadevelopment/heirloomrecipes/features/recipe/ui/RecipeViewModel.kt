package com.bogadevelopment.heirloomrecipes.features.recipe.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogadevelopment.heirloomrecipes.core.auth.AuthRepository
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipeRepository
import com.bogadevelopment.heirloomrecipes.features.register.data.ProfileRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RecipeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val recipeRepo: RecipeRepository,
    private val profileRepo: ProfileRepository,
    private val authRepository: AuthRepository
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState

    private val id: Int = savedStateHandle.get<Int>("id") ?: error("Recipe id is required")

    private val _showSaveSuccess = MutableStateFlow(false)  // Variable to control the Toast
    val showSaveSuccess: StateFlow<Boolean> = _showSaveSuccess

    private var currentProfileId: Int? = null

    init {
        loadCurrentProfile()
    }

    private fun loadCurrentProfile() {
        viewModelScope.launch {
            try {
                val firebaseUid = authRepository.currentUser
                firebaseUid.collect { profileData ->
                    if (profileData != null) {
                        val profile = profileRepo.getProfileByFirebaseUid(profileData.toString())
                        currentProfileId = profile?.id
                        loadRecipe()
                    }else{
                        currentProfileId = null
                    }
                }

            } catch (e: Exception) {
                println("Error cargando perfil: ${e.message}")
            }
        }
    }

    fun onExpandedChanged(type: String) {
        _uiState.update { state ->
            if (type == "ingredients") {
                state.copy(ingredientsExpanded = !_uiState.value.ingredientsExpanded)
            } else {
                state.copy(stepsExpanded = !_uiState.value.stepsExpanded)
            }
        }
    }

    fun onIngredientChanged(ingredients: String) {
        _uiState.update {
            it.copy(ingredients = ingredients)
        }
    }

    fun onStepsChanged(steps: String) {
        _uiState.update {
            it.copy(steps = steps)
        }
    }

    fun onSaveRecipe() {
        val profileId = currentProfileId ?: return
        viewModelScope.launch {
            recipeRepo.updateRecipeById(
                id = id,
                profileId = profileId,
                title = _uiState.value.title,
                ingredients = _uiState.value.ingredients,
                steps = _uiState.value.steps
            )
            _showSaveSuccess.value = true  // Save success
        }
    }

    fun onToastShown() {
        _showSaveSuccess.value = false
    }

    private fun loadRecipe() {
        val profileId = currentProfileId ?: return
        viewModelScope.launch {
            val recipe = recipeRepo.getRecipeById(id, profileId)
            recipe?.let {
                _uiState.update {
                    it.copy(
                        title = recipe.title,
                        ingredients = recipe.ingredients,
                        steps = recipe.steps
                    )
                }
            }
        }
    }

}

data class RecipeUiState(
    val title: String = "",
    val ingredients: String = "",
    val steps: String = "",
    val ingredientsExpanded: Boolean = false,
    val stepsExpanded: Boolean = false
)
