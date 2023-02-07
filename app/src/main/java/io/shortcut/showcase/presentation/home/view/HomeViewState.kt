package io.shortcut.showcase.presentation.home.view

import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.presentation.common.filter.data.CountryFilter
import io.shortcut.showcase.presentation.data.ShowcaseBannerUI
import io.shortcut.showcase.presentation.home.data.CategorySection
import io.shortcut.showcase.presentation.showAll.SheetContent
import io.shortcut.showcase.util.mock.genMockBanners

data class HomeViewState(
    val categorizedApps: List<CategorySection> = emptyList(),

    val banners: List<ShowcaseBannerUI> = genMockBanners(),

    // States
    val refreshing: Boolean = false,
    val loading: Boolean = false,

    val totalTimerTime: Long = 30000L, // 30: Seconds
    val isTimerRunning: Boolean = true,

    val bottomSheet: SheetContent = SheetContent.None,
    val screenshotGalleryIndex: Int = 0,

    val filterButtons: List<CountryFilter> = emptyList(),
    val activeCountryFilter: Country = Country.All
)
