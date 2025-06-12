package com.bogadevelopment.heirloomrecipes.designs

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val lightColorScheme = lightColorScheme(
    background = Color(0xFF004453)
)

val darkColorScheme = darkColorScheme(
    primary = Color(0xFF4DB6AC),
    secondary = Color(0xFF003845),
    tertiary = Color(0xFF0D6276),
    surfaceVariant = Color(0x40FFFFFF),     // Tags
    background = Color(0xFF004453),        // Backgrounds
    onBackground = Color(0xFFFFFFFF),      // Tittles
)

@Composable
fun CmpTheme(content : @Composable () -> Unit){
    val isDarkTheme = isSystemInDarkTheme()
    val colorScheme = if(isDarkTheme) darkColorScheme else lightColorScheme

    MaterialTheme(content = content, colorScheme = colorScheme)
}