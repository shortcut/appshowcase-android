package io.shortcut.showcase.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun ExtendedShowcaseTheme(
    content: @Composable () -> Unit
) {
    val showcaseTypography = ShowcaseTypography(
        h1 = TextStyle(
            fontFamily = fontFamilyRoboto,
            fontSize = 18.sp,
            fontWeight = FontWeight.W700,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
            textAlign = TextAlign.Left
        ),
        h2 = TextStyle(
            fontFamily = fontFamilyRoboto,
            fontSize = 16.sp,
            fontWeight = FontWeight.W700,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
            textAlign = TextAlign.Left
        ),
        h3 = TextStyle(
            fontFamily = fontFamilyRoboto,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
            textAlign = TextAlign.Left
        ),
        body = TextStyle(
            fontFamily = fontFamilyRoboto,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
            textAlign = TextAlign.Left
        ),
        bodySmall = TextStyle(
            fontFamily = fontFamilyRoboto,
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp,
            textAlign = TextAlign.Left
        ),
        bodySmallItalic = TextStyle(
            fontFamily = fontFamilyRoboto,
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 16.sp,
            letterSpacing = 0.sp,
            textAlign = TextAlign.Left
        ),
        button = TextStyle(
            fontFamily = fontFamilyRoboto,
            fontSize = 14.sp,
            fontWeight = FontWeight.W500,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
            textAlign = TextAlign.Left
        )
    )

    val showcaseColors = ShowcaseColors(
        // Showcase colors.
        ShowcaseBackground = Color(0xFF161616),
        ShowcasePrimary = Color(0xFF001AFF),
        ShowcaseSecondary = Color(0xFFFFFFFF),
        ShowcaseTertiary = Color(0xFF1C1B1F),
        ShowcaseDarkGrey = Color(0xFF2E2E2E),
        ShowcaseGrey = Color(0xFF6D6D6D),
        ShowcaseLightGrey = Color(0xFF8A8A8A),

        // This is used for gradients, 100 - 0.
        ShowcaseOverlay = Color(0xFF161616),

        // Showcase category colors.
        ShowcaseCategoryNavigation = Color(0xFF008555),
        ShowcaseCategoryBusiness = Color(0xFF42788E),
        ShowcaseCategoryEducation = Color(0xFFD57224),
        ShowcaseCategoryEntertainment = Color(0xFFB73B22),
        ShowcaseCategoryFood = Color(0xFFF5C554),
        ShowcaseCategoryShopping = Color(0xFF78B432),
        ShowcaseCategoryTravel = Color(0xFF6A5079),
        ShowcaseCategoryHealth = Color(0xFF2060CB),
        ShowcaseCategoryOther = Color(0xFFD9D9D9)
    )

    CompositionLocalProvider(
        LocalShowcaseTypography provides showcaseTypography,
        LocalShowcaseColors provides showcaseColors
    ) {
        MaterialTheme(
            content = content
        )
    }
}

// Use with eg. ShowcaseTheme.typography.body
object ShowcaseThemeCustom {
    val typography: ShowcaseTypography
        @Composable
        get() = LocalShowcaseTypography.current

    val colors: ShowcaseColors
        @Composable
        get() = LocalShowcaseColors.current


}