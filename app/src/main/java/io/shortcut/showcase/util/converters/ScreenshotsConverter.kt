package io.shortcut.showcase.util.converters

import androidx.room.TypeConverter
import io.shortcut.showcase.data.local.Screenshots
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ScreenshotsConverter {

    @TypeConverter
    fun screenshotToJson(value: Screenshots) = Json.encodeToString(value)

    @TypeConverter
    fun jsonToScreenshot(value: String) = Json.decodeFromString<Screenshots>(value)
}