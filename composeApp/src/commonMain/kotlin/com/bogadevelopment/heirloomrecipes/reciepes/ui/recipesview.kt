@file:OptIn(ExperimentalMaterial3Api::class)

package com.bogadevelopment.heirloomrecipes.reciepes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.bogadevelopment.heirloomrecipes.login.ui.CustomText


@Composable
fun RecipesView() {
    Scaffold(
        topBar = { ToolBar() },
        content ={Content()},
        floatingActionButton = {FAB()}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar(){
    TopAppBar(
        title = {CustomText("Heirloom Recipes", 25)},
        colors = TopAppBarColors(
            containerColor = Color.Gray,
            scrolledContainerColor = Color.Yellow,
            navigationIconContentColor = Color.Cyan,
            titleContentColor = Color.White,
            actionIconContentColor = Color.Black
        )
    )
}

@Composable
fun FAB() {
    FloatingActionButton(onClick = {} ) {
        Icon(Icons.Default.Add, contentDescription = "FAB")
    }
}

@Composable
fun Content() {
    Column(modifier = Modifier.fillMaxSize().background(Color.Blue)) {

    }
}