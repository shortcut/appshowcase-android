package io.shortcut.showcase.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

@Immutable
data class ShowcaseColors(
    val ShowcaseBackground: Color,
    val ShowcasePrimary: Color,
    val ShowcaseSecondary: Color,
    val ShowcaseTertiary: Color,
    val ShowcaseDarkGrey: Color,
    val ShowcaseGrey: Color,
    val ShowcaseLightGrey: Color,

    val ShowcaseOverlay: Color,

    val ShowcaseCategoryNavigation: Color,
    val ShowcaseCategoryBusiness: Color,
    val ShowcaseCategoryEducation: Color,
    val ShowcaseCategoryEntertainment: Color,
    val ShowcaseCategoryFood: Color,
    val ShowcaseCategoryShopping: Color,
    val ShowcaseCategoryTravel: Color,
    val ShowcaseCategoryHealth: Color,
    val ShowcaseCategoryOther: Color
)

val LocalShowcaseColors = staticCompositionLocalOf {
    ShowcaseColors(
        ShowcaseBackground = Color.Unspecified,
        ShowcasePrimary = Color.Unspecified,
        ShowcaseSecondary = Color.Unspecified,
        ShowcaseTertiary = Color.Unspecified,
        ShowcaseDarkGrey = Color.Unspecified,
        ShowcaseGrey = Color.Unspecified,
        ShowcaseLightGrey = Color.Unspecified,

        ShowcaseOverlay = Color.Unspecified,

        ShowcaseCategoryNavigation = Color.Unspecified,
        ShowcaseCategoryBusiness = Color.Unspecified,
        ShowcaseCategoryEducation = Color.Unspecified,
        ShowcaseCategoryEntertainment = Color.Unspecified,
        ShowcaseCategoryFood = Color.Unspecified,
        ShowcaseCategoryShopping = Color.Unspecified,
        ShowcaseCategoryTravel = Color.Unspecified,
        ShowcaseCategoryHealth = Color.Unspecified,
        ShowcaseCategoryOther = Color.Unspecified,
    )
}