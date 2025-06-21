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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    val state = viewModel.state

    Scaffold(
        topBar = { ToolBar("Heirloom Recipes") },
    ){ innerPadding ->
        Content(innerPadding, state, viewModel)
    }
}

@Composable
fun Content(
    innerPadding: PaddingValues,
    state: RegisterViewModel.UiState,
    viewModel: RegisterViewModel
){

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var repeatPassword by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)){
        Column(modifier = Modifier.align(Alignment.Center).offset(y = (-90).dp).padding(horizontal = 20.dp)){
            VerticalSpacer(40)
            CustomTittle("New Account", MaterialTheme.typography.titleLarge, MaterialTheme.colorScheme.onBackground)
            VerticalSpacer(60)
            Field(email, { email = it }, "Email", state.error != null, "email")
            VerticalSpacer(10)
            Field(password, { password = it }, "Password", state.error != null,"password")
            VerticalSpacer(5)
            Field(repeatPassword, { repeatPassword = it }, "Repeat password", state.error != null,"password")
            VerticalSpacer(20)
            GeneralButton("Create Account", 20, Modifier, { viewModel.register(email, password, repeatPassword)},{viewModel.registerEnable(email, password, repeatPassword)})
        }
    }


}