package com.bogadevelopment.heirloomrecipes.features.login.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState : StateFlow<LoginUiState> = _uiState

    fun onEmailChanged(email: String) {
        _uiState.update { state ->
            state.copy(email = email)
        }
        verifyLogin()
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { state ->
            state.copy(password = password)
        }
        verifyLogin()
    }

    private fun verifyLogin(){
        if(isEmailValid() && isPasswordValid() && fieldsNotEmpty()){
            _uiState.update {
                it.copy(isLoginEnable = true)
            }
        }else{
            _uiState.update {
                it.copy(isLoginEnable = false)
            }
        }
    }

    private fun isEmailValid(): Boolean = Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()
    private fun isPasswordValid(): Boolean = _uiState.value.password.length >= 8
    private fun fieldsNotEmpty() : Boolean = _uiState.value.email.isNotEmpty() && _uiState.value.password.isNotEmpty()

}


data class LoginUiState (
    val email : String = "",
    val password : String = "",
    val loggedIn : Boolean = false,
    val isLoginEnable : Boolean = false,
    val error : String? = null
)
