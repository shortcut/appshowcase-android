package io.shortcut.showcase.presentation.home.view

import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.presentation.common.filter.data.CountryFilter
import io.shortcut.showcase.presentation.common.topbar.SearchWidgetState
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.presentation.data.ShowcaseBannerUI
import io.shortcut.showcase.presentation.home.data.CategorySection
import io.shortcut.showcase.presentation.showAll.SheetContent
import io.shortcut.showcase.util.mock.genMockBanners

data class HomeState(
    val isRefreshing: Boolean = false,
    val bottomSheet: SheetContent = SheetContent.None,
    val loading: Boolean = false,
    val homeGridState: HomeAppsGridState = HomeAppsGridState(),
    val homeAppSearchState: HomeAppSearchState? = null,
    val toolbarState: SearchWidgetState = SearchWidgetState.CLOSED
)

data class HomeAppsGridState(
    val categorizedApps: List<CategorySection> = emptyList(),
    val banners: List<ShowcaseBannerUI> = genMockBanners(),
    val totalTimerTime: Long = 30000L, // 30: Seconds
    val isTimerRunning: Boolean = true,
    val screenshotGalleryIndex: Int = 0,
    val filterButtons: List<CountryFilter> = emptyList(),
    val activeCountryFilter: Country = Country.All,
)

data class HomeAppSearchState(
    val query: String,
    val searchResults: List<ShowcaseAppUI>,
    val bottomSheet: SheetContent = SheetContent.None,
)
