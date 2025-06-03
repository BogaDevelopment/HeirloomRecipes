package com.bogadevelopment.heirloomrecipes

import androidx.compose.runtime.*
import com.bogadevelopment.heirloomrecipes.login.ui.LoginScreen
import com.bogadevelopment.heirloomrecipes.login.ui.LoginViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    LoginScreen(LoginViewModel())
}