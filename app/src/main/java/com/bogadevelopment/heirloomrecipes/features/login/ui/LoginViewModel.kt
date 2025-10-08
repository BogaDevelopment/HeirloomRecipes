package com.bogadevelopment.heirloomrecipes.features.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogadevelopment.heirloomrecipes.core.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState : StateFlow<LoginUiState> = _uiState

    fun login(){

        viewModelScope.launch {
            try {
                val userSession = authRepository.login(_uiState.value.email, _uiState.value.password)
                if (userSession != null) {
                    _uiState.update { it.copy(loggedIn = true, error = null) }
                } else {
                    _uiState.update { it.copy(loggedIn = false, error = "Usuario o contraseña incorrectos") }
                    clearFields()
                }
            } catch (e: Exception) {
            _uiState.update { it.copy(loggedIn = false, error = e.message ?: "Error desconocido") }
                clearFields()
            }
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


    private fun clearFields(){
        _uiState.update {
            it.copy(email = "", password = "")
        }
    }

}


data class LoginUiState (
    val email : String = "",
    val password : String = "",
    val loggedIn : Boolean = false,
    val error : String? = null
)
