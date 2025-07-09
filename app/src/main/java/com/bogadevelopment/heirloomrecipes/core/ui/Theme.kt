package com.bogadevelopment.heirloomrecipes.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bogadevelopment.heirloomrecipes.R


val lightColorScheme = lightColorScheme(
    surfaceVariant = Color(0x40FFFFFF),     // Tags
    background = Color(0xFF004453),        // Backgrounds
    onBackground = Color(0xFFFFFFFF),      // Tittles
)

val darkColorScheme = darkColorScheme(
    primary = Color(0xFF4DB6AC),
    secondary = Color(0xFF003845),
    tertiary = Color(0xFF0D6276),
    surface = Color(0xCC333333),
    surfaceVariant = Color(0x40FFFFFF),     // Tags
    background = Color(0xFF004453),        // Backgrounds
    onBackground = Color(0xFFFFFFFF),      // Tittles
    outlineVariant = Color(0x99FFFFFF),       //Transparent
    error = Color(0xFFFF0000)
)




@Composable
fun CustomTheme(content : @Composable () -> Unit){

    val isDarkTheme = isSystemInDarkTheme()
    val colorScheme = if(isDarkTheme) darkColorScheme else lightColorScheme

    val tinosFontFamily = FontFamily(
        Font(R.font.tinos_regular, FontWeight.Normal),
        Font(R.font.tinos_bold, FontWeight.Bold)
    )

    val typography = Typography(
        headlineLarge = TextStyle(
            fontSize = 40.sp,
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
            fontSize = 40.sp
        ),
        titleMedium = TextStyle(
            fontSize = 30.sp
        ),
        titleSmall = TextStyle(
            fontSize = 15.sp
        ),
        displayMedium = TextStyle(
            fontSize = 20.sp
        ),
    ).defaultFontFamily(tinosFontFamily)




    MaterialTheme(content = content, colorScheme = colorScheme, typography = typography )
}

fun Typography.defaultFontFamily(fontFamily: FontFamily): Typography {
    return this.copy(
        displayLarge = this.displayLarge.copy(fontFamily = fontFamily),
        displayMedium = this.displayMedium.copy(fontFamily = fontFamily),
        displaySmall = this.displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = this.headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = this.headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = this.headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = this.titleLarge.copy(fontFamily = fontFamily),
        titleMedium = this.titleMedium.copy(fontFamily = fontFamily),
        titleSmall = this.titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = this.bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = this.bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = this.bodySmall.copy(fontFamily = fontFamily),
        labelLarge = this.labelLarge.copy(fontFamily = fontFamily),
        labelMedium = this.labelMedium.copy(fontFamily = fontFamily),
        labelSmall = this.labelSmall.copy(fontFamily = fontFamily)
    )
}
