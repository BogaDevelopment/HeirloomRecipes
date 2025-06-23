package com.bogadevelopment.heirloomrecipes

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bogadevelopment.heirloomrecipes.login.ui.LoginScreen
import com.bogadevelopment.heirloomrecipes.reciepes.ui.RecipesScreen
import com.bogadevelopment.heirloomrecipes.recipe.ui.RecipeScreen
import com.bogadevelopment.heirloomrecipes.recipe.ui.RecipeViewModel
import com.bogadevelopment.heirloomrecipes.register.ui.RegisterScreen
import com.bogadevelopment.heirloomrecipes.themes.CustomTheme
import dev.gitlive.firebase.auth.FirebaseUser
import org.jetbrains.compose.ui.tooling.preview.Preview



@Composable
@Preview
fun App() {

    val firebaseUser : FirebaseUser? by remember { mutableStateOf(null) }

    val startScreen : Any = if (firebaseUser == null) LoginScreen else RecipesScreen

    CustomTheme {
        val navController = rememberNavController()
        NavHost(navController, startDestination = startScreen) {

            composable<LoginScreen> {
                LoginScreen(onLoggedIn = {
                    navController.navigate(RecipesScreen){
                        popUpTo(LoginScreen){ inclusive = true }
                    }
                                         }, toRegisterView = {navController.navigate(RegisterScreen)})
            }
            composable<RegisterScreen> {
                RegisterScreen(onRegister = { navController.navigate(LoginScreen)})
            }
            composable<RecipesScreen>{
                RecipesScreen(onItemClick = {navController.navigate(RecipeScreen(it.id))}, onLogOut = {
                    navController.navigate(LoginScreen){
                        popUpTo(RecipesScreen){ inclusive = true }
                    }
                })
            }
            composable<RecipeScreen> {  backStackEntry ->
                val recipe = backStackEntry.toRoute<RecipeScreen>()
                RecipeScreen(viewModel {RecipeViewModel(recipe.id)}) }
        }
    }

}