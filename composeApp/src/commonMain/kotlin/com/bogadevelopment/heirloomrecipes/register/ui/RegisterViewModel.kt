package com.bogadevelopment.heirloomrecipes.register.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState : MutableStateFlow<RegisterUiState> = _uiState


    data class RegisterUiState (
        val email : String = "",
        val password: String = "",
        val repeatPassword : String = "",
        val registered : Boolean = false,
        val isRegisterEnable : Boolean = false,
        val error : String? = null
    )

    fun register() {

        if (!_uiState.value.isRegisterEnable) {
            _uiState.update {
                it.copy(registered = false, error = null)
            }
            return
        }

        viewModelScope.launch {
            try {
                Firebase.auth.createUserWithEmailAndPassword(_uiState.value.email, _uiState.value.password)
                _uiState.update {
                    it.copy(registered = true)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(registered = false, error = e.message)
                }
                clearFields()
            }
        }

    }

    fun onEmailChanged(email: String) {
        _uiState.update { state ->
            state.copy(email = email)
        }
        verifyRegister()
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { state ->
            state.copy(password = password)
        }
        verifyRegister()
    }

    fun onRepeatPasswordChanged(password: String) {
        _uiState.update { state ->
            state.copy(repeatPassword = password)
        }
        verifyRegister()
    }



    private fun verifyRegister(){
        val enabledRegister : Boolean = isEmailValid(_uiState.value.email) && isPasswordValid(_uiState.value.password) && fieldsNotEmpty() && _uiState.value.password == _uiState.value.repeatPassword
        _uiState.update {
            it.copy(isRegisterEnable = enabledRegister)
        }
    }


    private fun isEmailValid(email : String) : Boolean = email.contains("@") && email.contains(".com")
    private fun isPasswordValid(password : String) : Boolean = password.length >= 8

    private fun fieldsNotEmpty() : Boolean {
        return _uiState.value.email.isNotEmpty() && _uiState.value.password.isNotEmpty() && _uiState.value.repeatPassword.isNotEmpty()
    }

    private fun clearFields(){
        _uiState.update {
            it.copy(email = "", password = "", repeatPassword = "")
        }
    }

}