package com.bogadevelopment.heirloomrecipes

import com.bogadevelopment.heirloomrecipes.features.recipe.ui.RecipeViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bogadevelopment.heirloomrecipes.core.ui.CustomTheme
import com.bogadevelopment.heirloomrecipes.features.login.ui.LoginScreen
import com.bogadevelopment.heirloomrecipes.features.recipe.ui.RecipeScreen
import com.bogadevelopment.heirloomrecipes.features.recipes.ui.RecipesScreen
import com.bogadevelopment.heirloomrecipes.features.register.ui.RegisterScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

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
    val firebaseUser: FirebaseUser? = remember { Firebase.auth.currentUser }
    val startScreen: Any = if (firebaseUser == null) LoginScreen else RecipesScreen

    NavHost(navController = navController, startDestination = LoginScreen){
        composable<LoginScreen>{
            LoginScreen(onLoggedIn = {
                navController.navigate(RecipesScreen) {
                    popUpTo(LoginScreen) { inclusive = true }
                }
            }, toRegisterView = { navController.navigate(RegisterScreen) })
        }

        composable<RegisterScreen> {
            RegisterScreen(
                onRegister = { navController.navigate(LoginScreen) },
                onBack = { navController.popBackStack() })
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
