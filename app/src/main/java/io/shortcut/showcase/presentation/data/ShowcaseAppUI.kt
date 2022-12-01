package io.shortcut.showcase.presentation.data

import io.shortcut.showcase.data.local.Histogram
import io.shortcut.showcase.data.local.Screenshots
import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.data.mapper.GeneralCategory

data class ShowcaseAppUI(
    val id: Int,

    val genreAndroid: String,
    val genreIos: String,
    val iconAndroid: String,
    val iconIos: String,
    val installsAndroid: String,
    val isShortcutApp: Boolean,
    val packageAndroid: String,
    val ratingAndroid: Float,
    val ratingIos: Float,
    val releasedAndroid: String,
    val releasedIos: String,
    val scoreTextAndroid: String,
    val scoreTextIos: Float,
    val summaryAndroid: String,
    val summaryIos: String,
    val titleAndroid: String,
    val titleIos: String,

    val generalCategory: GeneralCategory,

    val country: Country,

    val screenshots: Screenshots,
    val histogram: Histogram,

    val onClick: () -> Unit = {}
)
