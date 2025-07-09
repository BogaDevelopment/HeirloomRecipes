package com.bogadevelopment.heirloomrecipes.core.ui.components.dialogs

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bogadevelopment.heirloomrecipes.core.ui.components.CustomButton
import com.bogadevelopment.heirloomrecipes.core.ui.components.CustomField
import com.bogadevelopment.heirloomrecipes.core.ui.components.CustomTittle
import com.bogadevelopment.heirloomrecipes.core.ui.components.VerticalSpacer

@Composable
fun NewRecipeDialog(
    show: Boolean,
    text: String,
    onTextChanged: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {


    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Card(
                Modifier.fillMaxWidth(),
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Color.Green,
                    disabledContainerColor = Color.Blue,
                    disabledContentColor = Color.Yellow
                )
            ) {
                VerticalSpacer(35)
                CustomTittle("New Recipe", MaterialTheme.typography.headlineMedium, MaterialTheme.colorScheme.onBackground)
                VerticalSpacer(30)
                CustomField(text, "Add tittle", onTextChanged)
                VerticalSpacer(30)
                Row(Modifier.fillMaxWidth().padding(bottom = 35.dp)) {
                    CustomButton("Cancel", 15, Modifier.weight(0.5f), "left") { onDismiss() }
                    CustomButton("Add", 15, Modifier.weight(0.5f), "right") { onConfirm() }
                }
            }
        }
    }

}