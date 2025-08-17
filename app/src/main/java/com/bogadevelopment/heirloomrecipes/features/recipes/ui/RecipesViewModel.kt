package com.bogadevelopment.heirloomrecipes.features.recipes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipeRepository
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipesCard
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
class RecipesViewModel @Inject constructor(
        private val recipeRepo : RecipeRepository,
        private val profileRepo: ProfileRepository
    ): ViewModel(){

    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState : StateFlow<RecipesUiState> = _uiState

    private var currentProfileId: Int? = null

    init{
        loadCurrentProfile()
    }

    private fun loadCurrentProfile() {
        viewModelScope.launch {
            try {
                val firebaseUid = Firebase.auth.currentUser?.uid
                if (firebaseUid != null) {
                    val profile = profileRepo.getProfileByFirebaseUid(firebaseUid)
                    currentProfileId = profile?.id
                    loadRecipes()
                }
            } catch (e: Exception) {
                println("Error cargando perfil: ${e.message}")
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

    private fun addRecipe() {
        val title = _uiState.value.title
        val profileId = currentProfileId
        if (title.isNotBlank() && profileId != null) {
            val newRecipe = RecipesCard(id = 0, profileId = profileId, title = title)
            viewModelScope.launch {
                recipeRepo.insert(newRecipe)
                loadRecipes()
            }
        }
        onDialogDismiss()
    }

    private fun deleteRecipe(id: Int) {
        val profileId = currentProfileId ?: return
        viewModelScope.launch {
            recipeRepo.deleteRecipeById(id =id, profileId = profileId)
            loadRecipes()
        }
    }

    private fun loadRecipes() {
        val profileId = currentProfileId ?: return
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(recipes = recipeRepo.getRecipesByProfileId(profileId))
                }
            } catch (e: Exception) {
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