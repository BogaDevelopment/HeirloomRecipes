package com.bogadevelopment.heirloomrecipes.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bogadevelopment.heirloomrecipes.login.ui.CustomText
import com.bogadevelopment.heirloomrecipes.login.ui.CustomTittle
import com.bogadevelopment.heirloomrecipes.login.ui.VerticalSpacer

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
                CustomTittle("New Recipe", 28, MaterialTheme.colorScheme.onBackground)
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

@Composable
fun CustomButton(
    name: String,
    textSize: Int,
    modifier: Modifier = Modifier,
    lado: String,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp
        ),
        modifier = if (lado == "left") modifier.padding(
            start = 15.dp,
            end = 5.dp
        ) else modifier.padding(end = 15.dp, start = 5.dp)
    ) {
        Text(name, fontSize = textSize.sp)
    }
}

@Composable
fun CustomField(name: String, label: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
        value = name,
        shape = RoundedCornerShape(20),
        onValueChange = { onTextChanged(it) },
        label = { CustomText(label, 15, MaterialTheme.colorScheme.onBackground) },
    )
}