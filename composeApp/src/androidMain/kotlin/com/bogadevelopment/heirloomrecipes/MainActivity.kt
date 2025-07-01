package com.bogadevelopment.heirloomrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bogadevelopment.heirloomrecipes.database.Database
import com.bogadevelopment.heirloomrecipes.reciepes.data.DataBaseDriverFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(Database.invoke(DataBaseDriverFactory(this@MainActivity).createDriver()))
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
}