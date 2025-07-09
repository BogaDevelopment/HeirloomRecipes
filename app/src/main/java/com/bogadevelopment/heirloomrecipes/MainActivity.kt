package com.bogadevelopment.heirloomrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bogadevelopment.heirloomrecipes.core.ui.CustomTheme
import com.bogadevelopment.heirloomrecipes.features.login.ui.LoginScreen
import com.bogadevelopment.heirloomrecipes.features.recipes.ui.RecipesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomTheme {
                NavigationWrapper()
            }
        }
    }
}

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = LoginScreen){
        composable<LoginScreen>{
            LoginScreen(onLoggedIn = {}, toRegisterView = {})
        }

        composable<RecipesScreen> {
            RecipesScreen({})
        }
    }
}
