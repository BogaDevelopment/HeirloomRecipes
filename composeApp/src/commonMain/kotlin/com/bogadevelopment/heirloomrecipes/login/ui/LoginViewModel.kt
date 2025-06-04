package com.bogadevelopment.heirloomrecipes.login.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    data class UiState (
        val loggedIn : Boolean = false,
        val error : String? = null
    )

    fun login(user : String, password : String){
        state = when{
            loginEnable(user, password) -> UiState(false, "Invalid")
            else -> UiState(true)
        }
    }

    private fun loginEnable (user : String, pass : String) : Boolean {
        return !user.contains("@") && !user.contains(".com") && pass.length >= 8 && user.isNotEmpty() && pass.isNotEmpty()
    }

}