package io.shortcut.showcase.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.w3c.dom.Text
import javax.annotation.concurrent.Immutable

@Immutable
data class ShowcaseTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val body: TextStyle,
    val bodySmall: TextStyle,
    val bodySmallItalic: TextStyle,
    val button: TextStyle,
)

val LocalShowcaseTypography = staticCompositionLocalOf {
    ShowcaseTypography(
        h1 = TextStyle.Default,
        h2 = TextStyle.Default,
        h3 = TextStyle.Default,
        body = TextStyle.Default,
        bodySmall = TextStyle.Default,
        bodySmallItalic = TextStyle.Default,
        button = TextStyle.Default
    )
}

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)