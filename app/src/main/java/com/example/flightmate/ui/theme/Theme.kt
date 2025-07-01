package com.example.flightmate.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Rose40,
    secondary = Plum40,
    tertiary = Plum40
)

private val LightColorScheme = lightColorScheme(
    primary = Rose40,
    secondary = Plum40,
    tertiary = Wood40,

    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,

    surfaceTint = Rose40,

    primaryContainer = Rose20,
    secondaryContainer = Plum20,
    tertiaryContainer = Wood20,

    onPrimaryContainer = Rose80,
    onSecondaryContainer = Plum80,
    onTertiaryContainer = Wood80,

    background = Gray10,
    onBackground = Gray90,

    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF93000A),

    surface = Gray10,
    onSurface = Gray90,
    surfaceVariant = Color(0xFFF2DDE2),
    onSurfaceVariant = Color(0xFF514347),

    outline = Color(0xFF837377),
    outlineVariant = Color(0xFFD5C2C6),

    scrim = Color.Black,

    inverseSurface = Color(0xFF372E30),
    inverseOnSurface = Color(0xFFFDEDEF),
    inversePrimary = Color(0xFFFFB0C8),

    surfaceDim = Color(0xFFE6D6D9),
    surfaceBright = Color(0xFFFFF8F8),
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Color(0xFFFFF0F2),
    surfaceContainer = Color(0xFFFAEAED),
    surfaceContainerHigh = Color(0xFFF5E4E7),
    surfaceContainerHighest = Color(0xFFEFDFE1),
)

@Composable
fun FlightMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
