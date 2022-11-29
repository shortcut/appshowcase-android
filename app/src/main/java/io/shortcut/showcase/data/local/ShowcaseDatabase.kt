package io.shortcut.showcase.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.shortcut.showcase.util.converters.LongConverter
import io.shortcut.showcase.util.converters.MapConverter
import io.shortcut.showcase.util.converters.ScreenshotsConverter

@Database(
    entities = [ShowcaseAppEntity::class],
    version = 1
)

@TypeConverters(
    ScreenshotsConverter::class,
    MapConverter::class,
    LongConverter::class
)
abstract class ShowcaseDatabase: RoomDatabase() {
    abstract val dao: ShowcaseDAO
}
