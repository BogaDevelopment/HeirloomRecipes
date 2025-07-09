package com.bogadevelopment.heirloomrecipes.core.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GeneralButton(
    text: String,
    shape: Int,
    modifier: Modifier,
    isLogin: () -> Unit,
    isValid: Boolean
) {
    Button(
        onClick = { isLogin() },
        shape = RoundedCornerShape(shape.dp),
        modifier = modifier.fillMaxWidth(),
        enabled = isValid
    ) {
        Text(text = text, fontSize = 20.sp)
    }
}

@Composable
fun CustomTittle(name: String, style: TextStyle, color: Color, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = name,
        style = style,
        textAlign = TextAlign.Center,
        color = color,
    )
}

@Composable
fun CustomText(name: String, style: TextStyle, color : Color, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = name,
        style = style,
        textAlign = TextAlign.Start,
        color = color
    )
}

@Composable
fun Field(text: String, onTextChanged: (String) -> Unit, ph: String, isError: Boolean, type : String) {
    var isPassVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { onTextChanged(it) },
        label = { CustomText(ph, MaterialTheme.typography.bodyMedium, MaterialTheme.colorScheme.onBackground) },
        isError = isError,
        shape = RoundedCornerShape(20),
        maxLines = 1,
        visualTransformation = if (isPassVisible || type != "password") VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if(type == "password") {
                IconButton(onClick = { isPassVisible  = !isPassVisible}) {
                    Icon(
                        imageVector = if(isPassVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (isPassVisible) "Hide pass" else "Show pass"
                    )

                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            errorBorderColor = MaterialTheme.colorScheme.error
        )
    )
}


// Spacers

@Composable
fun VerticalSpacer(size: Int) {
    Spacer(modifier = Modifier.height(size.dp))
}
