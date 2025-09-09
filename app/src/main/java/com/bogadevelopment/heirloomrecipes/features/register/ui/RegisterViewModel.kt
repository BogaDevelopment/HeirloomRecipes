package com.bogadevelopment.heirloomrecipes.features.register.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogadevelopment.heirloomrecipes.core.auth.AuthRepository
import com.bogadevelopment.heirloomrecipes.features.register.data.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: MutableStateFlow<RegisterUiState> = _uiState

    fun register() {
        viewModelScope.launch {
            val result = authRepository.register(
                email = _uiState.value.email,
                password = _uiState.value.password,
                name = _uiState.value.name,
                lastName = _uiState.value.lastName
            )

            result.onSuccess { profile ->
                try {
                    profileRepository.insertProfile(profile)
                    _uiState.update { it.copy(registered = true, error = null) }
                } catch (e: Exception) {
                    _uiState.update { it.copy(registered = false, error = e.message) }
                }
            }.onFailure { e ->
                _uiState.update { it.copy(registered = false, error = e.message) }
                clearFields()
            }
        }
    }

    fun onNameChanged(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onLastNameChanged(lastName: String) {
        _uiState.update { it.copy(lastName = lastName) }
    }

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onRepeatPasswordChanged(password: String) {
        _uiState.update { it.copy(repeatPassword = password) }
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
