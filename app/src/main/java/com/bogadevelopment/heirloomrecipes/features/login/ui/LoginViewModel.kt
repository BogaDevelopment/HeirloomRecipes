package com.bogadevelopment.heirloomrecipes.features.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogadevelopment.heirloomrecipes.core.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState : StateFlow<LoginUiState> = _uiState

    fun login(){

        viewModelScope.launch {
            val result = authRepository.login(uiState.value.email, uiState.value.password)
            result.onSuccess {
                _uiState.update {
                    it.copy(loggedIn = true, error = null)
                }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(loggedIn = false, error = e.message)
                }
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
