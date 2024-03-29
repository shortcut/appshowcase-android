package io.shortcut.showcase.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.shortcut.showcase.data.local.ShowcaseDAO
import io.shortcut.showcase.data.local.ShowcaseDatabase
import io.shortcut.showcase.domain.remote.FirebaseService
import io.shortcut.showcase.domain.remote.FirebaseServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppService(): FirebaseService = FirebaseServiceImpl()

    @Provides
    @Singleton
    fun provideShowcaseDatabase(
        app: Application
    ): ShowcaseDatabase {
        return Room.databaseBuilder(
            app,
            ShowcaseDatabase::class.java,
            "showcasedb.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideShowcaseDAO(
        db: ShowcaseDatabase
    ): ShowcaseDAO = db.showcaseDAO
}
