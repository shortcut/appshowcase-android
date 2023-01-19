package io.shortcut.showcase.domain.repository

import io.shortcut.showcase.presentation.data.ShowcaseBannerUI
import io.shortcut.showcase.util.resource.Resource
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepository {

    suspend fun fetchBanners(): Flow<Resource<List<ShowcaseBannerUI>>>
}