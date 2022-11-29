package io.shortcut.showcase.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import io.shortcut.showcase.R

val fontFamilyGotham = FontFamily(
    listOf(
        Font(
            resId = R.font.gothambook,
            weight = FontWeight.Normal
        ),
        Font(
            resId = R.font.gothambold,
            weight = FontWeight.Bold
        )
    )
)

val fontFamilyRoboto = FontFamily(
    listOf(
        Font(
            resId = R.font.roboto_thin,
            weight = FontWeight.Thin,
        ),
        Font(
            resId = R.font.roboto_thinitalic,
            weight = FontWeight.Thin,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.roboto_light,
            weight = FontWeight.Light,
        ),
        Font(
            resId = R.font.roboto_lightitalic,
            weight = FontWeight.Light,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.roboto_medium,
            weight = FontWeight.Medium,
        ),
        Font(
            resId = R.font.roboto_mediumitalic,
            weight = FontWeight.Medium,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.roboto_medium,
            weight = FontWeight.Medium,
        ),
        Font(
            resId = R.font.roboto_mediumitalic,
            weight = FontWeight.Medium,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.roboto_bold,
            weight = FontWeight.Bold,
        ),
        Font(
            resId = R.font.roboto_bolditalic,
            weight = FontWeight.Bold,
            style = FontStyle.Italic
        ),
    )
)