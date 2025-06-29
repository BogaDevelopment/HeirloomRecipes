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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.bogadevelopment.heirloomrecipes.login.ui.CustomText
import com.bogadevelopment.heirloomrecipes.reciepes.ui.ToolBar
import kotlinx.serialization.Serializable

@Serializable
data class RecipeScreen(val id : Int)

@Composable
fun RecipeScreen(viewModel: RecipeViewModel, onBack: () -> Unit) {
    Scaffold(
        topBar = { ToolBar(viewModel.state?.tittle ?: "No tittle",
            Icons.AutoMirrored.Filled.ArrowBack) {onBack()} },
    ) { innerPadding ->
        Content(innerPadding)
    }
}


@Composable
fun Content(innerPadding: PaddingValues) {

    Column(
        modifier = Modifier.
            fillMaxSize().
            background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(top = 3.dp, bottom = 3.dp)
    ) {
        ExpandableGeneralCard(
            "Ingredients",0.3f ,Modifier.padding(horizontal = 10.dp).padding(top = 5.dp)
        )
        ExpandableGeneralCard("Steps",1f ,Modifier.padding(horizontal = 10.dp).padding(top = 5.dp))

    }
}

@Composable
fun ExpandableGeneralCard(tittle: String,maxHeightFraction: Float ,modifier: Modifier) {

    var text by remember { mutableStateOf(TextFieldValue("")) }
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()){
            val maxCardHeight = maxHeight * maxHeightFraction

            Column(
                Modifier.background(MaterialTheme.colorScheme.secondary)
                    .clickable { expanded = !expanded }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomText(
                        tittle,
                        MaterialTheme.typography.titleMedium,
                        MaterialTheme.colorScheme.onBackground,
                        Modifier.padding(top = 3.dp, bottom = 3.dp, start = 5.dp)
                    )
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Collar" else "Expand"
                    )
                }

                if (expanded) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
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