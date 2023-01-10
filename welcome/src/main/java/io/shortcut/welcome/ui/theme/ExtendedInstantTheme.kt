package io.shortcut.welcome.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun ExtendedInstantTheme(
    content: @Composable () -> Unit
) {
    val instantTypography = InstantTypography(
        sloganTitle = TextStyle(
            fontFamily = fontFamilyGotham,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
            textAlign = TextAlign.Left
        )
    )

    CompositionLocalProvider(
        LocalInstantTypography provides instantTypography
    ) {
        MaterialTheme(
            content = content
        )
    }
}

object InstantThemeCustom {
    val typography: InstantTypography
        @Composable
        get() = LocalInstantTypography.current
}