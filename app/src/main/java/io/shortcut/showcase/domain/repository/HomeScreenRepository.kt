package io.shortcut.showcase.domain.repository

import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.presentation.data.ShowcaseBannerUI
import io.shortcut.showcase.util.resource.Resource
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepository {

    suspend fun fetchAppsFromRemote(selectedCountry: Country): Flow<Resource<List<ShowcaseAppUI>>>

    suspend fun fetchAppsFromDatabase(activeCountryFilter: Country): Flow<Resource<List<ShowcaseAppUI>>>

    suspend fun fetchBanners(): Flow<Resource<List<ShowcaseBannerUI>>>
}