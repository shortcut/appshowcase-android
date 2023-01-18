package io.shortcut.showcase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.shortcut.showcase.domain.repository.HomeScreenRepository
import io.shortcut.showcase.domain.repository.HomeScreenRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHomeScreenRepository(
        homeScreenRepositoryImpl: HomeScreenRepositoryImpl
    ): HomeScreenRepository
}