package com.bogadevelopment.heirloomrecipes

import com.bogadevelopment.heirloomrecipes.features.recipe.ui.RecipeViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bogadevelopment.heirloomrecipes.core.ui.CustomTheme
import com.bogadevelopment.heirloomrecipes.features.login.ui.LoginScreen
import com.bogadevelopment.heirloomrecipes.features.recipe.ui.RecipeScreen
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

    NavHost(navController = navController, startDestination = RecipesScreen){
        composable<LoginScreen>{
            LoginScreen(onLoggedIn = {
                navController.navigate(RecipesScreen) {
                    popUpTo(LoginScreen) { inclusive = true }
                }
            }, toRegisterView = {})
        }

        composable<RecipesScreen> {
            RecipesScreen(onItemClick = {navController.navigate(RecipeScreen(it.id, it.tittle))})
        }

        composable<RecipeScreen> { backStackEntry ->
            val recipe = backStackEntry.toRoute<RecipeScreen>()
            RecipeScreen(
                viewModel { RecipeViewModel(recipe.id) },
                recipe.tittle,
                onBack = { navController.popBackStack() })
        }
    }
}
