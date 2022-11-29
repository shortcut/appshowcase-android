package io.shortcut.showcase.util.converters

import androidx.room.TypeConverter
import io.shortcut.showcase.data.local.Histogram
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MapConverter {

    @TypeConverter
    fun histogramToJson(value: Histogram) = Json.encodeToString(value)

    @TypeConverter
    fun jsonToHistogram(value: String) = Json.decodeFromString<Histogram>(value)
}