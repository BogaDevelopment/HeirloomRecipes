package com.bogadevelopment.heirloomrecipes.features.register.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogadevelopment.heirloomrecipes.features.register.data.ProfileData
import com.bogadevelopment.heirloomrecipes.features.register.data.ProfileRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: MutableStateFlow<RegisterUiState> = _uiState

    fun register() {

        viewModelScope.launch {
            try {
                val authResult = Firebase.auth.createUserWithEmailAndPassword(
                    _uiState.value.email,
                    _uiState.value.password
                ).await()

                val firebaseUid = authResult.user?.uid ?: throw Exception("No se obtuvo el UID de Firebase")

                val profile = ProfileData(
                    name = _uiState.value.name,
                    lastName = _uiState.value.lastName,
                    email = _uiState.value.email,
                    firebaseUid = firebaseUid
                )

                profileRepository.insertProfile(profile)

                _uiState.update { it.copy(registered = true) }

            } catch (e: Exception) {
                _uiState.update { it.copy(registered = false, error = e.message) }
                println("Error en el registro: ${e.message}")
                clearFields()
            }
        }

    }

    fun onNameChanged(name: String) {
        _uiState.update { state ->
            state.copy(name = name)
        }
    }

    fun onLastNameChanged(lastName: String) {
        _uiState.update { state ->
            state.copy(lastName = lastName)
        }
    }


    fun onEmailChanged(email: String) {
        _uiState.update { state ->
            state.copy(email = email)
        }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { state ->
            state.copy(password = password)
        }
    }

    fun onRepeatPasswordChanged(password: String) {
        _uiState.update { state ->
            state.copy(repeatPassword = password)
        }
    }

    private fun clearFields() {
        _uiState.update {
            it.copy(name = "", lastName = "", email = "", password = "", repeatPassword = "")
        }
    }
}

data class RegisterUiState(
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val registered: Boolean = false,
    val error: String? = null
)
