package com.bogadevelopment.heirloomrecipes.features.recipes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bogadevelopment.heirloomrecipes.core.ui.components.CustomText
import com.bogadevelopment.heirloomrecipes.core.ui.components.dialogs.NewRecipeDialog
import com.bogadevelopment.heirloomrecipes.core.ui.components.drawers.DrawerContent
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipesCard
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object RecipesScreen

@Composable
fun RecipesScreen(onItemClick : (RecipesCard) -> Unit, viewModel : RecipesViewModel = viewModel()){

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = Modifier.background(MaterialTheme.colorScheme.tertiary),
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(/*onLogout = { scope.launch {
            viewModel.logout()
            onLogOut()
        }}*/)},
    ){
        Scaffold(
            topBar = { ToolBar("Heirloom Recipes", Icons.Default.Menu){ scope.launch { drawerState.open() }} },
            floatingActionButton = {FAB(viewModel)},
        ){ innerPadding ->
            Content(viewModel, innerPadding, onItemClick)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar(text : String, icon : ImageVector, onClick : () -> Unit){
    TopAppBar(
        title = {CustomText(text, MaterialTheme.typography.titleMedium, MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(start = 5.dp))},
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            scrolledContainerColor = Color.Yellow,
            navigationIconContentColor = Color.Cyan,
            titleContentColor = Color.White,
            actionIconContentColor = Color.Black
        ),
        navigationIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Drawer Icon",
                modifier = Modifier
                    .clickable { onClick() }
                    .padding(start = 5.dp),
                tint = MaterialTheme.colorScheme.onBackground

            )
        }
    )
}

@Composable
fun FAB(viewModel: RecipesViewModel) {
    FloatingActionButton(onClick = { viewModel.openDialog() }, containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onBackground) {
        Icon(Icons.Default.Add, contentDescription = "FAB")
    }
}

@Composable
fun Content(
    viewModel: RecipesViewModel,
    innerPadding: PaddingValues,
    onItemClick: (RecipesCard) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        NewRecipeDialog(uiState.show,uiState.tittle, { viewModel.onTittleChanged(it) } ,{viewModel.onDialogDismiss()}, {viewModel.onDialogConfirm()})
        RecipesList(innerPadding, viewModel, onItemClick, uiState)
    }
}

@Composable
fun RecipesList(
    innerPadding: PaddingValues,
    viewModel: RecipesViewModel,
    onItemClick: (RecipesCard) -> Unit,
    uiState: RecipesUiState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ){
        itemsIndexed(uiState.recipes, key = { _, item -> item.id }) { index, item ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 10.dp)
                .padding(top = 5.dp)
                .clickable { onItemClick(item) }, elevation = CardDefaults.cardElevation(8.dp)){
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.tertiary), contentAlignment = Alignment.CenterStart){
                    CustomText(item.tittle, MaterialTheme.typography.headlineSmall, MaterialTheme.colorScheme.onBackground, Modifier
                        .padding(start = 10.dp)
                        .wrapContentWidth())
                    IconButton(onClick = {viewModel.onExpandedChanged()}, modifier = Modifier.align(Alignment.CenterEnd)){
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More actions")
                    }
                    Box(modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .offset(y = (15).dp)){
                        DropdownMenu(expanded = uiState.expanded, onDismissRequest = { viewModel.onDialogDismiss() }, modifier = Modifier.background(
                            MaterialTheme.colorScheme.surface)){
                            DropdownMenuItem(
                                text = { Text("Delete", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground) },
                                onClick = { viewModel.onDialogDismiss(); viewModel.deleteIdRecipe(item.id)})
                        }
                    }
                }
            }

        }
    }
}