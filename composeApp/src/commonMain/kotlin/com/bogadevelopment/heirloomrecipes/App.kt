package com.bogadevelopment.heirloomrecipes

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bogadevelopment.heirloomrecipes.login.ui.LoginScreen
import com.bogadevelopment.heirloomrecipes.reciepes.ui.RecipesScreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = LoginScreen) {
        composable<LoginScreen> {
            LoginScreen(onLoggedIn = { navController.navigate(RecipesScreen)})
        }
        composable<RecipesScreen> { RecipesScreen() }
    }
}