package io.shortcut.showcase.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.shortcut.showcase.data.local.ShowcaseDatabase
import io.shortcut.showcase.data.remote.FirebaseServiceImpl
import io.shortcut.showcase.domain.model.ShowcaseAppAPI
import io.shortcut.showcase.domain.model.ShowcaseBannerAPI
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
}

abstract class FirebaseService {
    abstract suspend fun getApps(): List<ShowcaseAppAPI?>?

    abstract suspend fun getBanners(): List<ShowcaseBannerAPI?>?
}
