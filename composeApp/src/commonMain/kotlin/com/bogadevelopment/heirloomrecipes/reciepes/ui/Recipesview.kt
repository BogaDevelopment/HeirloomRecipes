@file:OptIn(ExperimentalMaterial3Api::class)

package com.bogadevelopment.heirloomrecipes.reciepes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        floatingActionButton = {FAB(viewModel)},
    ){ innerPadding ->
        Content(viewModel, innerPadding)
    }
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
fun Content(viewModel: RecipesViewModel, innerPadding: PaddingValues) {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        NewRecipeDialog(viewModel.show,viewModel.text,{viewModel.text = it} ,{viewModel.onDialogDismiss()}, {viewModel.onDialogConfirm()})
        RecipesList(innerPadding, viewModel)
    }
}

@Composable
fun RecipesList(innerPadding: PaddingValues, viewModel: RecipesViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ){
        itemsIndexed(viewModel.recipe, key = {_, item -> item.id}){ index, item ->
            Card(modifier = Modifier.fillMaxWidth().height(70.dp).padding(horizontal = 10.dp).padding(top = 5.dp), elevation = CardDefaults.cardElevation(8.dp)){
                Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.tertiary), contentAlignment = Alignment.CenterStart){
                    CustomText(item.tittle, MaterialTheme.typography.headlineSmall, MaterialTheme.colorScheme.onBackground, Modifier.padding(start = 10.dp).wrapContentWidth())
                    IconButton(onClick = {}, modifier = Modifier.align(Alignment.CenterEnd)){
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More actions")
                    }
                    // Dropdown menu
                }
            }

        }
    }
}