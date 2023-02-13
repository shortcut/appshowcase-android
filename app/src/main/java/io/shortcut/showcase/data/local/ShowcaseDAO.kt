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

    @Query("SELECT * FROM ShowcaseAppEntity WHERE country in (:countries)")
    suspend fun fetchAppsWithCountry(countries: List<String>): List<ShowcaseAppEntity>

    @Query("SELECT * FROM ShowcaseAppEntity WHERE country in (:countries) AND generalCategory = :category")
    suspend fun fetchAppsWithCountry(
        countries: List<String>,
        category: String
    ): List<ShowcaseAppEntity>

    @Query("SELECT * FROM ShowcaseAppEntity WHERE title like '%' || :query || '%'")
    suspend fun searchAppsWithName(query: String): List<ShowcaseAppEntity>

    @Query("DELETE FROM ShowcaseAppEntity")
    suspend fun deleteAllApps()
}