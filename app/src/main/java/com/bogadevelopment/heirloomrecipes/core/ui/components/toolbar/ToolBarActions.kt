package com.bogadevelopment.heirloomrecipes.core.ui.components.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.bogadevelopment.heirloomrecipes.core.ui.components.CustomText

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
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false}, modifier = Modifier.background(
                MaterialTheme.colorScheme.surface)){
                DropdownMenuItem(
                    text = { Text("Save", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground) },
                    onClick = { expanded = false; onAction() })
            }
        }
    )
}