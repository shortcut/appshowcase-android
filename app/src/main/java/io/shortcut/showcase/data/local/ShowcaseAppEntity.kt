package io.shortcut.showcase.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.data.mapper.GeneralCategory
import kotlinx.serialization.Serializable

@Entity
data class ShowcaseAppEntity(
    @PrimaryKey
    val id: String = "",

    val title: String,
    val iconUrl: String,
    val publisher: String,
    val country: Country,
    val screenshots: Screenshots,
    val totalInstalls: String,
    val shortDescription: String,

    val generalCategory: GeneralCategory,

    val highestRating: Float,
    // val totalHistogram: Histogram,

    val androidPackageID: String
)

@Serializable
data class Screenshots(
    val imageURLs: List<String>
)

@Serializable
data class Histogram(
    val histogramData: Map<String, Int>
)

class CountryTypeConverter {
    @TypeConverter
    fun toCountryType(value: String): Country = enumValueOf(value)

    @TypeConverter
    fun fromCountryType(value: Country): String = value.name
}