package com.bogadevelopment.heirloomrecipes.features.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bogadevelopment.heirloomrecipes.core.ui.components.CustomText
import com.bogadevelopment.heirloomrecipes.core.ui.components.CustomTittle
import com.bogadevelopment.heirloomrecipes.core.ui.components.Field
import com.bogadevelopment.heirloomrecipes.core.ui.components.GeneralButton
import com.bogadevelopment.heirloomrecipes.core.ui.components.VerticalSpacer
import kotlinx.serialization.Serializable

@Serializable
object LoginScreen

@Composable
fun LoginScreen(
    onLoggedIn : () -> Unit,
    toRegisterView : () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {


    Box(
        Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
    ) {
        Header()
        Body(Modifier.align(Alignment.Center).offset(y = (-30).dp).padding(horizontal = 20.dp), viewModel, toRegisterView, onLoggedIn)
        Footer(Modifier.align(Alignment.BottomCenter))
    }
}

// Main Components

@Composable
fun Header() {
}

@Composable
fun Footer(modifier: Modifier){
    CustomTittle("@Bogadevelopment",
        MaterialTheme.typography.titleSmall, MaterialTheme.colorScheme.surfaceVariant, modifier.padding(bottom = 10.dp))
}

@Composable
fun Body(
    modifier: Modifier,
    viewModel: LoginViewModel,
    toRegisterView: () -> Unit,
    onLoggedIn : () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = modifier) {
        VerticalSpacer(40)
        CustomTittle("Welcome", MaterialTheme.typography.titleLarge, MaterialTheme.colorScheme.onBackground)
        VerticalSpacer(60)
        Field(uiState.email, { viewModel.onEmailChanged(it) }, "Email", uiState.error != null, "email")
        VerticalSpacer(10)
        Field(uiState.password, { viewModel.onPasswordChanged(it) }, "Password", uiState.error != null ,"password")
        VerticalSpacer(5)
        CustomText("Forgot password ?", MaterialTheme.typography.bodyMedium,
            MaterialTheme.colorScheme.onBackground , Modifier.align(Alignment.End))
        VerticalSpacer(20)
        GeneralButton("Log in", 20, Modifier, { onLoggedIn() },uiState.isLoginEnable)
        VerticalSpacer(40)
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
            CustomText("Don't have an account?", MaterialTheme.typography.bodyMedium, MaterialTheme.colorScheme.onBackground)
            CustomText("Register", MaterialTheme.typography.bodyMedium,
                MaterialTheme.colorScheme.primary , Modifier.align(Alignment.CenterVertically).padding(start = 5.dp).clickable { toRegisterView() })

        }
    }
}
