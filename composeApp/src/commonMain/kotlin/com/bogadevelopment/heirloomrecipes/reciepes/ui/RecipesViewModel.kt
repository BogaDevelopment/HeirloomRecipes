package com.bogadevelopment.heirloomrecipes.reciepes.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RecipesViewModel : ViewModel(){

    var show by mutableStateOf(false)
        private set

    fun onDialogConfirm(){

    }

    fun onDialogDismiss(){
        show = false
    }

    fun openDialog(){
        show = true
    }

}