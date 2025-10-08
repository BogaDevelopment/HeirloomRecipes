package com.bogadevelopment.heirloomrecipes.features.register.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogadevelopment.heirloomrecipes.core.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: MutableStateFlow<RegisterUiState> = _uiState

    fun register() {

        viewModelScope.launch {
            try {
                val userSession = authRepository.register(_uiState.value.email, _uiState.value.password)

                if (userSession != null) {
                    _uiState.update { it.copy(registered = true, error = null) }
                } else {
                    _uiState.update { it.copy(registered = false, error = "Error al registrar usuario") }
                }
            } catch (e: Exception) {
                val msg = when (e.message) {
                    "The email address is already in use by another account." ->
                        "El email ya está registrado."
                    else -> e.message ?: "Error desconocido."
                }
                _uiState.update { it.copy(registered = false, error = msg) }
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
