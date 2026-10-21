package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightWhiteBlueColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = WhitePure,
    primaryContainer = BluePillBg,
    onPrimaryContainer = BlueSecondary,
    secondary = BlueSecondary,
    onSecondary = WhitePure,
    secondaryContainer = BlueLightBg,
    onSecondaryContainer = BluePrimary,
    tertiary = BlueAccent,
    background = BlueLightBg,
    onBackground = BlueTextPrimary,
    surface = BlueCardBg,
    onSurface = BlueTextPrimary,
    surfaceVariant = BlueLightBg,
    onSurfaceVariant = BlueTextSecondary,
    outline = BlueBorder,
    outlineVariant = BlueBorderSubtle,
    error = StatusRed,
    onError = WhitePure
)

private val DarkBlueColorScheme = darkColorScheme(
    primary = BlueAccent,
    onPrimary = BlueTextPrimary,
    primaryContainer = DarkBlueCard,
    onPrimaryContainer = WhitePure,
    secondary = BluePrimary,
    onSecondary = WhitePure,
    background = DarkBlueBg,
    onBackground = WhitePure,
    surface = DarkBlueCard,
    onSurface = WhitePure,
    surfaceVariant = DarkBlueBg,
    onSurfaceVariant = BlueBorderSubtle,
    outline = DarkBlueBorder,
    error = StatusRed,
    onError = WhitePure
)

@Composable
fun ElhnafyTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkBlueColorScheme else LightWhiteBlueColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
