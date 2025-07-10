package com.bogadevelopment.heirloomrecipes.features.recipe.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bogadevelopment.heirloomrecipes.core.ui.components.cards.ExpandableGeneralCard
import com.bogadevelopment.heirloomrecipes.core.ui.components.toolbar.ToolBarActions
import kotlinx.serialization.Serializable

@Serializable
data class RecipeScreen(val id: Int, val tittle: String)

@Composable
fun RecipeScreen(viewModel: RecipeViewModel, tittle : String, onBack: () -> Unit) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ToolBarActions(
                tittle,
                Icons.AutoMirrored.Filled.ArrowBack,
                { onBack() },
                onAction = {/*save*/}
            )
        },
    ) { innerPadding ->
        Content(
            innerPadding,
            viewModel,
            uiState
        )
    }
}

@Composable
fun Content(innerPadding: PaddingValues, viewModel: RecipeViewModel, uiState: RecipeUiState) {

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(top = 3.dp, bottom = 3.dp)
    ) {
        ExpandableGeneralCard(
            "Ingredients",
            uiState.ingredients,
            0.3f,
            Modifier.padding(horizontal = 10.dp).padding(top = 5.dp),
            uiState.ingredientsExpanded,
            { viewModel.onExpandedChanged("ingredients") },
            {viewModel.onIngredientChanged(it)}
        )
        ExpandableGeneralCard(
            "Steps",
            uiState.steps,
            1f,
            Modifier.padding(horizontal = 10.dp).padding(top = 5.dp),
            uiState.stepsExpanded,
            { viewModel.onExpandedChanged("steps") },
            { viewModel.onStepsChanged(it)}
        )

    }
}