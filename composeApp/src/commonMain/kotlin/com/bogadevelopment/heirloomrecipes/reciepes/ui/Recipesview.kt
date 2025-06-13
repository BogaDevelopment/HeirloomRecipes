@file:OptIn(ExperimentalMaterial3Api::class)

package com.bogadevelopment.heirloomrecipes.reciepes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bogadevelopment.heirloomrecipes.dialogs.NewRecipeDialog
import com.bogadevelopment.heirloomrecipes.login.ui.CustomText
import kotlinx.serialization.Serializable


@Serializable
object RecipesScreen

@Composable
fun RecipesScreen(viewModel: RecipesViewModel = viewModel()){

    Scaffold(
        topBar = { ToolBar() },
        content ={Content(viewModel)},
        floatingActionButton = {FAB(viewModel)},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar(){
    TopAppBar(
        title = {CustomText("Heirloom Recipes", MaterialTheme.typography.headlineMedium, MaterialTheme.colorScheme.onBackground)},
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            scrolledContainerColor = Color.Yellow,
            navigationIconContentColor = Color.Cyan,
            titleContentColor = Color.White,
            actionIconContentColor = Color.Black
        )
    )
}

@Composable
fun FAB(viewModel: RecipesViewModel) {
    FloatingActionButton(onClick = { viewModel.openDialog() }, containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onBackground) {
        Icon(Icons.Default.Add, contentDescription = "FAB")
    }
}

@Composable
fun Content(viewModel: RecipesViewModel) {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        NewRecipeDialog(viewModel.show,viewModel.text,{viewModel.text = it} ,{viewModel.onDialogDismiss()}, {viewModel.onDialogConfirm()})
    }
}