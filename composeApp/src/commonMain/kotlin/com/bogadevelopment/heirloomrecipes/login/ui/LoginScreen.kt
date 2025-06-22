package com.bogadevelopment.heirloomrecipes.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.serialization.Serializable


@Serializable
object LoginScreen

@Composable
fun LoginScreen(
    onLoggedIn : () -> Unit,
    toRegisterView : () -> Unit,
    viewModel: LoginViewModel = viewModel()) {

    val state = viewModel.state

    LaunchedEffect(viewModel.state.loggedIn){
        if(viewModel.state.loggedIn) onLoggedIn()
    }

    Box(
        Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
    ) {
        Header()
        Body(Modifier.align(Alignment.Center).offset(y = (-30).dp).padding(horizontal = 20.dp), viewModel, state, toRegisterView)
        Footer(Modifier.align(Alignment.BottomCenter))
    }
}

// Main Components

@Composable
fun Header() {
}

@Composable
fun Footer(modifier: Modifier){
    CustomTittle("@Bogadevelopment",MaterialTheme.typography.titleSmall, MaterialTheme.colorScheme.surfaceVariant, modifier.padding(bottom = 10.dp))
}

@Composable
fun Body(
    modifier: Modifier,
    viewModel: LoginViewModel,
    state: LoginViewModel.UiState,
    toRegisterView: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier) {
        VerticalSpacer(40)
        CustomTittle("Welcome", MaterialTheme.typography.titleLarge, MaterialTheme.colorScheme.onBackground)
        VerticalSpacer(60)
        Field(email, { email = it }, "Email", state.error != null, "email")
        VerticalSpacer(10)
        Field(password, { password = it }, "Password", state.error != null,"password")
        VerticalSpacer(5)
        CustomText("Forgot password ?", MaterialTheme.typography.bodyMedium,MaterialTheme.colorScheme.onBackground , Modifier.align(Alignment.End))
        VerticalSpacer(20)
        GeneralButton("Log in", 20, Modifier, { viewModel.login(email, password)},{viewModel.loginEnable(email, password)})
        VerticalSpacer(40)
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
            CustomText("Don't have an account?", MaterialTheme.typography.bodyMedium,MaterialTheme.colorScheme.onBackground)
            CustomText("Register", MaterialTheme.typography.bodyMedium,MaterialTheme.colorScheme.primary , Modifier.align(Alignment.CenterVertically).padding(start = 5.dp).clickable { toRegisterView() })

        }
    }
}



// General components

@Composable
fun GeneralButton(
    text: String,
    shape: Int,
    modifier: Modifier,
    isLogin: () -> Unit,
    isValid: () -> Boolean
) {
    Button(
        onClick = { isLogin() },
        shape = RoundedCornerShape(shape.dp),
        modifier = modifier.fillMaxWidth(),
        enabled = isValid()
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