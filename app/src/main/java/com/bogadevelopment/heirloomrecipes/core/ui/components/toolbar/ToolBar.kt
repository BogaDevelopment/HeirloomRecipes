package com.bogadevelopment.heirloomrecipes.core.ui.components.toolbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.bogadevelopment.heirloomrecipes.core.ui.components.CustomText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar(text : String, icon : ImageVector, onClick : () -> Unit){
    TopAppBar(
        title = { CustomText(text, MaterialTheme.typography.titleMedium, MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(start = 5.dp)) },
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