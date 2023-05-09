package com.rk.searchbar.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.*


@Suppress("unused")
private val DarkColorPalette = darkColors(
    primary = DarkGray,
    primaryVariant = Teal,
    secondary = Orange
)

private val LightColorPalette = lightColors(
    primary = DarkGray,
    primaryVariant = Teal,
    secondary = Orange
)

@Composable
fun AppTheme(
    @Suppress("UNUSED_PARAMETER") darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}