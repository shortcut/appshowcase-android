package io.shortcut.showcase.presentation.home.view

import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.presentation.common.filter.data.FilterButtonData
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.presentation.data.ShowcaseBannerUI
import io.shortcut.showcase.presentation.home.data.CategorySection
import io.shortcut.showcase.util.mock.genMockBanners
import io.shortcut.showcase.util.mock.genMockShowcaseAppUI

data class HomeViewState(
    // Data
    val apps: List<ShowcaseAppUI> = emptyList(),
    val categorySections: List<CategorySection> = emptyList(),

    val banners: List<ShowcaseBannerUI> = genMockBanners(),

    // States
    val refreshing: Boolean = false,
    val loading: Boolean = false,

    val totalTimerTime: Long = 30000L, // 30: Seconds
    val isTimerRunning: Boolean = true,

    val appInView: ShowcaseAppUI = genMockShowcaseAppUI(),
    val screenshotGalleryIndex: Int = 0,

    val filterButtons: List<FilterButtonData> = emptyList(),
    val activeCountryFilter: Country = Country.Norway
)
