package com.bogadevelopment.heirloomrecipes.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState : StateFlow<LoginUiState> = _uiState


    data class LoginUiState (
        val email : String = "",
        val password : String = "",
        val loggedIn : Boolean = false,
        val isLoginEnable : Boolean = false,
        val error : String? = null
    )

    fun login(){

        if(!_uiState.value.isLoginEnable) {
            _uiState.update {
                it.copy(loggedIn = false, error = null)
            }
            return
        }

        viewModelScope.launch {
            try {
                Firebase.auth.signInWithEmailAndPassword(_uiState.value.email, _uiState.value.password)
                _uiState.update {
                    it.copy(loggedIn = true)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(loggedIn = false, error = e.message)
                }
                clearFields()
            }
        }
    }

    private fun clearFields(){
        _uiState.update {
            it.copy(email = "", password = "")
        }
    }

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
        val enabledLogin : Boolean = isEmailValid(_uiState.value.email) && isPasswordValid(_uiState.value.password) && fieldsNotEmpty()
        _uiState.update {
            it.copy(isLoginEnable = enabledLogin)
        }
    }

    private fun isEmailValid(email : String) : Boolean = email.contains("@") && email.contains(".com")
    private fun isPasswordValid(password : String) : Boolean = password.length >= 8

    private fun fieldsNotEmpty() : Boolean {
        return _uiState.value.email.isNotEmpty() && _uiState.value.password.isNotEmpty()
    }

}
