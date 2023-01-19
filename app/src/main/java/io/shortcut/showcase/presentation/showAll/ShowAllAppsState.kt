package io.shortcut.showcase.presentation.showAll

import io.shortcut.showcase.data.mapper.GeneralCategory
import io.shortcut.showcase.presentation.common.filter.data.CountryFilter
import io.shortcut.showcase.presentation.data.ShowcaseAppUI

data class ShowAllAppsState(
    val countryFilter: List<CountryFilter> = emptyList(),
    val selectedCategory: GeneralCategory = GeneralCategory.BUSINESS,
    val apps: List<ShowcaseAppUI> = emptyList(),
    val allCategories: List<GeneralCategory> = GeneralCategory.values().toList(),
    val isLoading: Boolean = true
)