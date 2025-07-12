package com.bogadevelopment.heirloomrecipes.core.ui.components.drawers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(onLogout: () -> Unit) {
    ModalDrawerSheet(
        drawerContentColor = MaterialTheme.colorScheme.onBackground,
        drawerContainerColor = MaterialTheme.colorScheme.background
    ) {
        Spacer(Modifier.height(16.dp))
        Text("Cerrar sesión", modifier = Modifier.padding(start = 10.dp).clickable { onLogout() })
    }

}