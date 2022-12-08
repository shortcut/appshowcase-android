package io.shortcut.showcase.presentation.data

import io.shortcut.showcase.data.local.Histogram
import io.shortcut.showcase.data.local.Screenshots
import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.data.mapper.GeneralCategory

data class ShowcaseAppUI(
    val id: Int,

    val title: String,
    val iconUrl: String,
    val publisher: String,
    val country: Country,
    val screenshots: Screenshots,
    val totalInstalls: String,
    val shortDescription: String,

    val generalCategory: GeneralCategory,

    val highestRating: String,
    // val totalHistogram: Histogram,

    val androidPackageID: String,

    val onClick: () -> Unit = {}
)
