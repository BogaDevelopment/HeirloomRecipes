package com.bogadevelopment.heirloomrecipes.features.login.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState : StateFlow<LoginUiState> = _uiState

    fun login(){

        viewModelScope.launch {
            try {
                Firebase.auth.signInWithEmailAndPassword(_uiState.value.email, _uiState.value.password).await()
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
