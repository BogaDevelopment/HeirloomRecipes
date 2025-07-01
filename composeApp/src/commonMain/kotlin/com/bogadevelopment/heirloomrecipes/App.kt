package com.bogadevelopment.heirloomrecipes

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bogadevelopment.heirloomrecipes.database.Database
import com.bogadevelopment.heirloomrecipes.login.ui.LoginScreen
import com.bogadevelopment.heirloomrecipes.reciepes.ui.RecipesScreen
import com.bogadevelopment.heirloomrecipes.recipe.ui.RecipeScreen
import com.bogadevelopment.heirloomrecipes.recipe.ui.RecipeViewModel
import com.bogadevelopment.heirloomrecipes.register.ui.RegisterScreen
import com.bogadevelopment.heirloomrecipes.themes.CustomTheme
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App(database: Database) {

    val firebaseUser: FirebaseUser? = remember { Firebase.auth.currentUser }
    val startScreen: Any = if (firebaseUser == null) LoginScreen else RecipesScreen


    CustomTheme {
        val navController = rememberNavController()
        NavHost(navController, startDestination = startScreen) {

            composable<LoginScreen> {
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
                RecipesScreen(
                    onItemClick = { navController.navigate(RecipeScreen(it.id)) },
                    onLogOut = {
                        navController.navigate(LoginScreen) {
                            popUpTo(RecipesScreen) { inclusive = true }
                        }
                    },
                    database
                )
            }
            composable<RecipeScreen> { backStackEntry ->
                val recipe = backStackEntry.toRoute<RecipeScreen>()
                RecipeScreen(
                    viewModel { RecipeViewModel(recipe.id, database) },
                    onBack = { navController.popBackStack() })
            }
        }
    }

}