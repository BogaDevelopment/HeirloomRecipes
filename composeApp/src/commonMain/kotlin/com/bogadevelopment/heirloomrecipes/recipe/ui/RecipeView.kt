package com.bogadevelopment.heirloomrecipes.recipe.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bogadevelopment.heirloomrecipes.login.ui.CustomText
import com.bogadevelopment.heirloomrecipes.reciepes.ui.ToolBar
import kotlinx.serialization.Serializable

@Serializable
data class RecipeScreen(val id: Int)

@Composable
fun RecipeScreen(viewModel: RecipeViewModel, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            ToolBarActions(
                viewModel.state?.tittle ?: "No tittle",
                Icons.AutoMirrored.Filled.ArrowBack,
                { onBack() },
                {}
            )
        },
    ) { innerPadding ->
        Content(innerPadding, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBarActions(text : String, icon : ImageVector, onClick : () -> Unit, onAction : () -> Unit){
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
                modifier = Modifier.clickable { onClick() }.padding(start = 5.dp),
                tint = MaterialTheme.colorScheme.onBackground

            )
        },
        actions = {
            var expanded by remember { mutableStateOf(false) }
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Options",
                modifier = Modifier.clickable { expanded = !expanded }.padding(end = 5.dp),
                tint = MaterialTheme.colorScheme.onBackground)
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false}, modifier = Modifier.background(MaterialTheme.colorScheme.surface)){
                DropdownMenuItem(
                    text = { Text("Delete", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground) },
                    onClick = { expanded = false; {onAction}})
            }
        }
    )
}


@Composable
fun Content(innerPadding: PaddingValues, viewModel: RecipeViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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

@Composable
fun ExpandableGeneralCard(
    tittle: String,
    text: String,
    maxHeightFraction: Float,
    modifier: Modifier,
    isExpanded: Boolean,
    onExpandedChanged: () -> Unit,
    onTextChanged: (String) -> Unit
) {

    Card(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val maxCardHeight = maxHeight * maxHeightFraction

            Column(
                Modifier.background(MaterialTheme.colorScheme.secondary)
                    .clickable { onExpandedChanged() }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomText(
                        tittle,
                        MaterialTheme.typography.displayMedium,
                        MaterialTheme.colorScheme.onBackground,
                        Modifier.padding(top = 3.dp, bottom = 3.dp, start = 5.dp)
                    )
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "Collar" else "Expand"
                    )
                }

                if (isExpanded) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)
                    OutlinedTextField(
                        value = text,
                        onValueChange = { onTextChanged(it) },
                        singleLine = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = maxCardHeight)
                            .padding(3.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                            focusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                        )
                    )
                }
            }
        }
    }
}