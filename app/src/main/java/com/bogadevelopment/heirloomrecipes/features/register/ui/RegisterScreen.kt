package com.bogadevelopment.heirloomrecipes.features.register.ui

import android.util.Patterns
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bogadevelopment.heirloomrecipes.core.ui.components.CustomTittle
import com.bogadevelopment.heirloomrecipes.core.ui.components.Field
import com.bogadevelopment.heirloomrecipes.core.ui.components.GeneralButton
import com.bogadevelopment.heirloomrecipes.core.ui.components.VerticalSpacer
import com.bogadevelopment.heirloomrecipes.features.recipes.ui.ToolBar
import kotlinx.serialization.Serializable

@Serializable
object RegisterScreen

@Composable
fun RegisterScreen(onRegister : () -> Unit, onBack : () -> Unit, viewModel: RegisterViewModel = viewModel()){

    Scaffold(
        topBar = { ToolBar("Heirloom Recipes", Icons.AutoMirrored.Filled.ArrowBack) {onBack()} },
    ){ innerPadding ->
        Content(innerPadding, viewModel,onRegister)
    }
}

@Composable
fun Content(
    innerPadding: PaddingValues,
    viewModel: RegisterViewModel,
    onRegister : () -> Unit
){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.registered){
        if(uiState.registered) onRegister()
    }

    val isRegisterEnabled by remember(uiState.name, uiState.lastName, uiState.email, uiState.password, uiState.repeatPassword){
        derivedStateOf {
            val nameValid = uiState.name.isNotBlank()
            val lastNameValid = uiState.lastName.isNotBlank()
            val emailValid = Patterns.EMAIL_ADDRESS.matcher(uiState.email).matches() && uiState.email.isNotBlank()
            val passwordValid = uiState.password.length >= 8 && uiState.password.isNotBlank()
            nameValid && lastNameValid && emailValid && passwordValid && uiState.password == uiState.repeatPassword
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)){
        Column(modifier = Modifier.align(Alignment.Center).offset(y = (-40).dp).padding(horizontal = 20.dp)){
            VerticalSpacer(40)
            CustomTittle("New Account", MaterialTheme.typography.titleLarge, MaterialTheme.colorScheme.onBackground)
            VerticalSpacer(60)
            Field(uiState.name, { viewModel.onNameChanged(it) }, "Name", uiState.error != null, "name")
            VerticalSpacer(5)
            Field(uiState.lastName, { viewModel.onLastNameChanged(it) }, "Last Name", uiState.error != null, "lastName")
            VerticalSpacer(5)
            Field(uiState.email, { viewModel.onEmailChanged(it) }, "Email", uiState.error != null, "email")
            VerticalSpacer(5)
            Field(uiState.password, { viewModel.onPasswordChanged(it) }, "Password", uiState.error != null,"password")
            VerticalSpacer(5)
            Field(uiState.repeatPassword, { viewModel.onRepeatPasswordChanged(it) }, "Repeat password", uiState.error != null,"password")
            VerticalSpacer(20)
            GeneralButton("Create Account", 20, Modifier, { viewModel.register()}, isRegisterEnabled)
        }
    }


}