package com.bogadevelopment.heirloomrecipes.features.register.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: MutableStateFlow<RegisterUiState> = _uiState

    fun register() {

        if (!_uiState.value.isRegisterEnable) {
            _uiState.update {
                it.copy(registered = false, error = null)
            }
            return
        }

        viewModelScope.launch {
            try {
                Firebase.auth.createUserWithEmailAndPassword(
                    _uiState.value.email,
                    _uiState.value.password
                )
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

    fun onNameChanged(name: String) {
        _uiState.update { state ->
            state.copy(name = name)
        }
        verifyRegister()
    }

    fun onLastNameChanged(lastName: String) {
        _uiState.update { state ->
            state.copy(lastName = lastName)
        }
        verifyRegister()
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


    private fun verifyRegister() {
        val enabledRegister: Boolean =
            isEmailValid() && isPasswordValid() && fieldsNotEmpty() && _uiState.value.password == _uiState.value.repeatPassword
        _uiState.update {
            it.copy(isRegisterEnable = enabledRegister)
        }
    }


    private fun isEmailValid(): Boolean = Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()
    private fun isPasswordValid(): Boolean = _uiState.value.password.length >= 8

    private fun fieldsNotEmpty(): Boolean {
        return _uiState.value.email.isNotEmpty() && _uiState.value.password.isNotEmpty() && _uiState.value.repeatPassword.isNotEmpty() && _uiState.value.name.isNotEmpty() && _uiState.value.lastName.isNotEmpty()
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
    val isRegisterEnable: Boolean = false,
    val error: String? = null
)
