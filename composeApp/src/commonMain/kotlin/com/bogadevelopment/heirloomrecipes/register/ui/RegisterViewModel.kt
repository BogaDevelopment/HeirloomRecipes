package com.bogadevelopment.heirloomrecipes.register.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bogadevelopment.heirloomrecipes.login.ui.LoginViewModel.UiState

class RegisterViewModel : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    data class UiState (
        val registered : Boolean = false,
        val error : String? = null
    )

    fun register(user : String, password : String, repeatPassword : String) {

        if (!registerEnable(user, password, repeatPassword)) {
            state = UiState(
                false,
                "Email or password incorrect"
            )
            return
        }
    }

    fun registerEnable (user : String, pass : String, pass2 : String) : Boolean {
        return user.contains("@") && user.contains(".com") && pass.length >= 8 && pass2.length >= 8 && pass == pass2 &&  fieldsNotEmpty(user, pass, pass2)
    }

    private fun fieldsNotEmpty(user : String, pass : String, pass2 : String) : Boolean {
        return user.isNotEmpty() && pass.isNotEmpty() && pass2.isNotEmpty()
    }

}