package com.bogadevelopment.heirloomrecipes

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bogadevelopment.heirloomrecipes.designs.CmpTheme
import com.bogadevelopment.heirloomrecipes.login.ui.LoginScreen
import com.bogadevelopment.heirloomrecipes.reciepes.ui.RecipesScreen
import com.bogadevelopment.heirloomrecipes.recipe.ui.RecipeScreen
import com.bogadevelopment.heirloomrecipes.recipe.ui.RecipeViewModel
import com.bogadevelopment.heirloomrecipes.themes.CustomTheme
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import org.jetbrains.compose.ui.tooling.preview.Preview



@Composable
@Preview
fun App() {

    val scope = rememberCoroutineScope()
    val auth = remember {Firebase.auth}
    var firebaseUser : FirebaseUser? by remember { mutableStateOf(null) }

    var startScreen : Any

    if(firebaseUser == null){
        startScreen = LoginScreen
    }else{
        startScreen = RecipesScreen
    }


    CustomTheme {
        val navController = rememberNavController()
        NavHost(navController, startDestination = startScreen) {

            composable<LoginScreen> {
                LoginScreen(onLoggedIn = { navController.navigate(RecipesScreen)})
            }
            composable<RecipesScreen>{
                RecipesScreen(onItemClick = {navController.navigate(RecipeScreen(it.id))})
            }
            composable<RecipeScreen> {  backStackEntry ->
                val recipe = backStackEntry.toRoute<RecipeScreen>()
                RecipeScreen(viewModel {RecipeViewModel(recipe.id)}) }
        }
    }

}