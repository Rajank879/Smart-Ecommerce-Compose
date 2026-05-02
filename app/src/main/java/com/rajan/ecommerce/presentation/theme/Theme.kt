package com.rajan.ecommerce.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.rajan.ecommerce.data.local.datastore.OnboardingDataStore

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0071CE),       // Deep Retail Blue
    onPrimary = Color.White,
    secondary = Color(0xFFFFC220),     // Highlight Gold
    onSecondary = Color(0xFF333333),   // Dark text on yellow for readability
    background = Color(0xFFF2F8FD),    // Airy light background
    onBackground = Color(0xFF333333),
    surface = Color.White,             // White cards
    onSurface = Color(0xFF333333),
    outline = Color(0xFFE0E0E0),       // Soft borders
    tertiary = Color(0xFF008300),       // Success Green for savings/discounts
    onSurfaceVariant = Color(0xFF757575),// Gray color for secondary text
    primaryFixed = Color(0xFF90CAF9)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),       // Lighter blue for dark mode
    onPrimary = Color.Black,
    secondary = Color(0xFFFFE082),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color(0xFFBDBDBD),
    primaryFixed = Color(0xFF90CAF9)
)

@Composable
fun EcommerceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    // Observe the DataStore
    val isDarkModePref by OnboardingDataStore.isDarkMode(context).collectAsState(initial = null)

    // Determine the actual theme to show
    val darkTheme = isDarkModePref ?: isSystemInDarkTheme()

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            window.navigationBarColor = Color.White.toArgb()

            val controller = WindowCompat.getInsetsController(window, view)
            controller.isAppearanceLightStatusBars = !darkTheme
            controller.isAppearanceLightNavigationBars = !darkTheme // ADD THIS

            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
//        SideEffect {
//            val window = (view.context as Activity).window
//            // We set the status bar to match the Airy Background
//            window.statusBarColor = colorScheme.primary.toArgb()
//            window.navigationBarColor = Color.White.toArgb()
//
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
//        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
