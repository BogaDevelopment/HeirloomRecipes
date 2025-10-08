package com.bogadevelopment.heirloomrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bogadevelopment.heirloomrecipes.core.auth.AuthRepository
import com.bogadevelopment.heirloomrecipes.core.ui.CustomTheme
import com.bogadevelopment.heirloomrecipes.features.login.ui.LoginScreen
import com.bogadevelopment.heirloomrecipes.features.recipe.ui.RecipeScreen
import com.bogadevelopment.heirloomrecipes.features.recipes.ui.RecipesScreen
import com.bogadevelopment.heirloomrecipes.features.register.ui.RegisterScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject



@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var authRepository: AuthRepository



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CustomTheme {
                NavigationWrapper(authRepository)
            }
        }
    }
}

@Composable
fun NavigationWrapper(authRepository: AuthRepository) {

    val navController = rememberNavController()
    val startScreen: Any = if (authRepository.getCurrentUser() == null) LoginScreen else RecipesScreen

    NavHost(navController = navController, startDestination = startScreen){
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
            RecipesScreen(onItemClick = {navController.navigate(RecipeScreen(it.id))},
                onLogOut = {
                    navController.navigate(LoginScreen) {
                        popUpTo(RecipesScreen) { inclusive = true }
                    }
                })
        }
        composable<RecipeScreen> { backStackEntry ->
            val recipe = backStackEntry.toRoute<RecipeScreen>()
            RecipeScreen(
                onBack = { navController.popBackStack() })
        }

    }
}
