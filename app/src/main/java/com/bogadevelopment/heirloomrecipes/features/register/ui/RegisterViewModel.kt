package com.bogadevelopment.heirloomrecipes.features.register.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: MutableStateFlow<RegisterUiState> = _uiState

    fun register() {

        viewModelScope.launch {
            try {
                Firebase.auth.createUserWithEmailAndPassword(
                    _uiState.value.email,
                    _uiState.value.password
                ).await()
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
