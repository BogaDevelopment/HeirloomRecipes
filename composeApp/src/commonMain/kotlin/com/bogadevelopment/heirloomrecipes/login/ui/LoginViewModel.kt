package com.bogadevelopment.heirloomrecipes.login.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    data class UiState (
        val loggedIn : Boolean = false,
        val error : String? = null
    )

    fun login(user : String, password : String){

        if(!loginEnable(user, password)) {
            state = UiState(false, "Email or password incorrect")
            return
        }

        viewModelScope.launch {
            try {
                Firebase.auth.signInWithEmailAndPassword(user, password)
                state = UiState(loggedIn = true)
            } catch (e: Exception) {
                state = UiState(false, "e.message")
            }
        }
    }

    fun loginEnable (user : String, pass : String) : Boolean {
        return user.contains("@") && user.contains(".com") && pass.length >= 8 && user.isNotEmpty() && pass.isNotEmpty()
    }

}