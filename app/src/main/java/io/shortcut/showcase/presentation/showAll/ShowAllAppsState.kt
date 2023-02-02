package io.shortcut.showcase.presentation.showAll

import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.data.mapper.GeneralCategory
import io.shortcut.showcase.data.mapper.SortOrder
import io.shortcut.showcase.presentation.common.filter.data.CountryFilter
import io.shortcut.showcase.presentation.data.ShowcaseAppUI

data class ShowAllAppsState(
    val countryFilter: List<CountryFilter> = emptyList(),
    val activeCountryFilter: Country = Country.All,
    val selectedCategory: GeneralCategory = GeneralCategory.BUSINESS,
    val apps: List<ShowcaseAppUI> = emptyList(),
    val allCategories: List<GeneralCategory> = GeneralCategory.values().toList(),
    val bottomSheet: SheetContent = SheetContent.None,
    val isLoading: Boolean = true,
    val selectedSortOrder: SortOrder = SortOrder.Rating
)

sealed class ShowAllAppEvent {
    object ShowBottomSheet : ShowAllAppEvent()
    object DismissBottomSheet : ShowAllAppEvent()
}

sealed class SheetContent {
    data class AppInfo(val app: ShowcaseAppUI) : SheetContent()
    data class Sort(val by: SortOrder) : SheetContent()
    object None : SheetContent()

    data class Category(val filter: GeneralCategory) : SheetContent()
}