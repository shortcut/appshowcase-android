package io.shortcut.showcase.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.data.mapper.GeneralCategory
import kotlinx.serialization.Serializable

@Entity
data class ShowcaseAppEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

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

    val screenshots: Screenshots,
    val histogram: Histogram,

    val generalCategory: GeneralCategory,

    val country: Country
)

@Serializable
data class Screenshots(
    val imagesAndroid: List<String>,
    val imagesIos: List<String>
)

@Serializable
data class Histogram(
    val histogramAndroid: Map<String, Int>,
    val histogramIos: Map<String, Int>,
)