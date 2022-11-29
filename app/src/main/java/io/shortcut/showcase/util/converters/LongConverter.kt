package io.shortcut.showcase.util.converters

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LongConverter {
    @TypeConverter
    fun longToString(value: Long) = Json.encodeToString(value)

    @TypeConverter
    fun stringToLong(value: String) = Json.decodeFromString<Long>(value)
}