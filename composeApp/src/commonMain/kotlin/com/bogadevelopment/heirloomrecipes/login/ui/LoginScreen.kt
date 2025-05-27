package com.bogadevelopment.heirloomrecipes.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen() {
    Box(
        Modifier.fillMaxSize().background(Color.Blue).padding(8.dp)
    ) {
        Header()
        Body(Modifier.align(alignment = Alignment.Center))
        Footer(Modifier.align(Alignment.BottomCenter))
    }
}

// Main Components

@Composable
fun Header() {
}

@Composable
fun Footer(modifier: Modifier){
    CustomText("@Bogadevelopment",20, modifier.padding(bottom = 20.dp))
}

@Composable
fun Body(modifier: Modifier) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier) {
        VerticalSpacer(40)
        CustomTittle("Welcome", 50)
        VerticalSpacer(60)
        Field(email, { email = it }, "Email")
        VerticalSpacer(10)
        Field(password, { password = it }, "Password")
        VerticalSpacer(5)
        CustomText("Forgot password ?", 15, Modifier.align(Alignment.End))
        VerticalSpacer(20)
        GeneralButton("Log in", 20, modifier)
    }
}

// General components

@Composable
fun GeneralButton(text: String, shape: Int, modifier: Modifier) {
    Button(
        onClick = {},
        shape = RoundedCornerShape(shape.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = text, fontSize = 20.sp)
    }
}

@Composable
fun CustomTittle(name: String, size: Int) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = name,
        fontSize = size.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
fun CustomText(name: String, size: Int, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = name,
        fontSize = size.sp,
        textAlign = TextAlign.Start
    )
}

@Composable
fun Field(text: String, onTextChanged: (String) -> Unit, ph: String) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { onTextChanged(it) },
        placeholder = { CustomText(ph, 20) }
    )
}


// Spacers

@Composable
fun VerticalSpacer(size: Int) {
    Spacer(modifier = Modifier.height(size.dp))
}