package com.bogadevelopment.heirloomrecipes.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


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


    val typography = Typography(
        headlineLarge = TextStyle(
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        ),
        headlineMedium = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        ),
        headlineSmall = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        ),
        bodyMedium = TextStyle(
            fontSize = 15.sp
        ),
        bodySmall = TextStyle(
            fontSize = 10.sp
        ),
        titleLarge = TextStyle(
            fontSize = 50.sp
        ),
        titleMedium = TextStyle(        // use to toolbar
            fontSize = 20.sp
        ),
        titleSmall = TextStyle(
            fontSize = 15.sp
        ),
    )


    MaterialTheme(content = content, colorScheme = colorScheme, typography = typography )
}
