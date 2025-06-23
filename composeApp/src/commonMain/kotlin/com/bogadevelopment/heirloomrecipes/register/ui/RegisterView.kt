package com.bogadevelopment.heirloomrecipes.register.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bogadevelopment.heirloomrecipes.reciepes.ui.ToolBar
import kotlinx.serialization.Serializable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bogadevelopment.heirloomrecipes.login.ui.CustomTittle
import com.bogadevelopment.heirloomrecipes.login.ui.Field
import com.bogadevelopment.heirloomrecipes.login.ui.GeneralButton
import com.bogadevelopment.heirloomrecipes.login.ui.VerticalSpacer

@Serializable
object RegisterScreen

@Composable
fun RegisterScreen(onRegister : () -> Unit, viewModel: RegisterViewModel = viewModel()){

    Scaffold(
        topBar = { ToolBar("Heirloom Recipes") },
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

    LaunchedEffect(viewModel.uiState.value.registered){
        if(viewModel.uiState.value.registered) onRegister()
    }

    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)){
        Column(modifier = Modifier.align(Alignment.Center).offset(y = (-90).dp).padding(horizontal = 20.dp)){
            VerticalSpacer(40)
            CustomTittle("New Account", MaterialTheme.typography.titleLarge, MaterialTheme.colorScheme.onBackground)
            VerticalSpacer(60)
            Field(uiState.email, { viewModel.onEmailChanged(it) }, "Email", uiState.error != null, "email")
            VerticalSpacer(10)
            Field(uiState.password, { viewModel.onPasswordChanged(it) }, "Password", uiState.error != null,"password")
            VerticalSpacer(5)
            Field(uiState.repeatPassword, { viewModel.onRepeatPasswordChanged(it) }, "Repeat password", uiState.error != null,"password")
            VerticalSpacer(20)
            GeneralButton("Create Account", 20, Modifier, { viewModel.register()}, uiState.isRegisterEnable)
        }
    }


}