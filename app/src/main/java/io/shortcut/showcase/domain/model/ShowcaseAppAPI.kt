package io.shortcut.showcase.domain.model

import io.shortcut.showcase.data.mapper.Country

data class ShowcaseAppAPI(
    val genreAndroid: String = "",
    val genreIos: String = "",
    val histogramAndroid: Map<String, Int> = emptyMap(),
    val histogramIos: Map<String, Int> = emptyMap(),
    val iconAndroid: String = "",
    val iconIos: String = "",
    val imagesAndroid: List<String> = emptyList(),
    val imagesIos: List<String> = emptyList(),
    val installsAndroid: String = "",
    val isShortcutApp: Boolean = true,
    val packageAndroid: String = "",
    // val idIos: Long = 0,
    val ratingAndroid: Float = 0f,
    val ratingIos: Float = 0f,
    val releasedAndroid: String = "",
    val releasedIos: String = "",
    val scoreTextAndroid: String = "",
    val scoreTextIos: Float = 0f,
    val summaryAndroid: String = "",
    val summaryIos: String = "",
    val titleAndroid: String = "",
    val titleIos: String = "",
    val country: String = ""

)
