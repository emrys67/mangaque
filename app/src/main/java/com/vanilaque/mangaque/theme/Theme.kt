package com.vanilaque.mangaque.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

sealed class ThemeColors(
    val background: Color,
    val surface: Color,
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val error: Color,
    val onBackground: Color
) {
    object Night : ThemeColors(
        primary = MangaPink,
        primaryVariant = MangaPurple,
        secondary = Color.LightGray,
        background = Color.Black,
        surface = Color.LightGray,
        error = Color.Red,
        onBackground = Color.White
    )

    object Day : ThemeColors(
        primary = MangaPink,
        primaryVariant = MangaPurple,
        secondary = Color.White,
        background = Color.White,
        surface = Color.White,
        error = Color.Red,
        onBackground = Color.Black
    )
}

private val DarkColorPalette = darkColors(
    primary = ThemeColors.Night.primary,
    primaryVariant = ThemeColors.Night.primaryVariant,
    secondary = ThemeColors.Night.secondary,
    background = ThemeColors.Night.background,
    surface = ThemeColors.Night.surface,
    error = ThemeColors.Night.error,
    onBackground = ThemeColors.Night.onBackground
)

private val LightColorPalette = lightColors(
    primary = ThemeColors.Day.primary,
    primaryVariant = ThemeColors.Day.primaryVariant,
    secondary = ThemeColors.Day.secondary,
    background = ThemeColors.Day.background,
    surface = ThemeColors.Day.surface,
    error = ThemeColors.Day.error,
    onBackground = ThemeColors.Day.onBackground
)


@Composable
fun MangaReaderTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}