package com.bogadevelopment.heirloomrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bogadevelopment.heirloomrecipes.core.auth.AuthRepository
import com.bogadevelopment.heirloomrecipes.core.ui.CustomTheme
import com.bogadevelopment.heirloomrecipes.features.login.ui.LoginScreen
import com.bogadevelopment.heirloomrecipes.features.recipe.ui.RecipeScreen
import com.bogadevelopment.heirloomrecipes.features.recipes.ui.RecipesScreen
import com.bogadevelopment.heirloomrecipes.features.register.data.ProfileRepository
import com.bogadevelopment.heirloomrecipes.features.register.ui.RegisterScreen
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
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
    var startScreen by remember { mutableStateOf<Any?>(null) }
    var isLoading by remember { mutableStateOf(true) }


    LaunchedEffect(authRepository) {
        authRepository.currentUser.collect { userProfileData ->
            if (isLoading) {
                startScreen = if (userProfileData != null) RecipesScreen else LoginScreen
                isLoading = false
            }
        }
    }

    if (isLoading) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Cargando...")
        }
        return
    }

    NavHost(navController = navController, startDestination = startScreen!!) {

        composable<LoginScreen> {
            LoginScreen(
                onLoggedIn = {
                    navController.navigate(RecipesScreen) {
                        popUpTo(LoginScreen) { inclusive = true }
                    }
                },
                toRegisterView = { navController.navigate(RegisterScreen) }
            )
        }

        composable<RegisterScreen> {
            RegisterScreen(
                onRegister = { navController.navigate(LoginScreen) },
                onBack = { navController.popBackStack() }
            )
        }

        composable<RecipesScreen> {
            RecipesScreen(
                onItemClick = { navController.navigate(RecipeScreen(it.id)) },
                onLogOut = {
                    navController.navigate(LoginScreen) {
                        popUpTo(RecipesScreen) { inclusive = true }
                    }
                }
            )
        }

        composable<RecipeScreen> { backStackEntry ->
            RecipeScreen(onBack = { navController.popBackStack() })
        }
    }
}
