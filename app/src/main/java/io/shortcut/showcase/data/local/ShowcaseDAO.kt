package io.shortcut.showcase.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShowcaseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApps(apps: List<ShowcaseAppEntity>)

    @Query("SELECT * FROM ShowcaseAppEntity")
    suspend fun fetchAllApps(): List<ShowcaseAppEntity>

    @Query("SELECT * FROM ShowcaseAppEntity WHERE country = :country")
    suspend fun fetchAppsWithCountry(country: String): List<ShowcaseAppEntity>

    @Query("DELETE FROM ShowcaseAppEntity")
    suspend fun deleteAllApps()
}