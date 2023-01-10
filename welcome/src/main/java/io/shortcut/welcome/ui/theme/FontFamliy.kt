package io.shortcut.welcome.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import io.shortcut.welcometoshortcut.R

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