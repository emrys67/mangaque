package com.vanilaque.mangaque.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color.Black,
    secondary = Color.White,
)

private val LightColorPalette = lightColors(
    primary = Color.Black,
    secondary = Color.White,
    surface = MangaPurple

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Immutable
data class ExtendedColors(
    val surfaceSecondary: Color,
    val surfaceThirdly: Color,
    val likeField: Color,
    val likeDisable: Color,
    val likeEnable: Color,
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        surfaceSecondary = Color.Unspecified,
        surfaceThirdly = Color.Unspecified,
        likeField = Color.Unspecified,
        likeDisable = Color.Unspecified,
        likeEnable = Color.Unspecified,
    )
}

@Composable
fun MangaReaderTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    val extendedColors: ExtendedColors =
        if (darkTheme)
            ExtendedColors(
                surfaceSecondary = MangaPink,
                surfaceThirdly = Color.LightGray,
                likeField = FieldColor,
                likeEnable = LikeColorChosen,
                likeDisable = LikeColor,
            )
        else
            ExtendedColors(
                surfaceSecondary = MangaPink,
                surfaceThirdly = Color.LightGray,
                likeField = FieldColor,
                likeEnable = LikeColorChosen,
                likeDisable = LikeColor,
            )

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