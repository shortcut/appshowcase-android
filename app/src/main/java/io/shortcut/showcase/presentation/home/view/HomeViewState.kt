package io.shortcut.showcase.presentation.home.view

import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.presentation.common.filter.data.FilterButtonData
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.presentation.home.data.CategorySection
import io.shortcut.showcase.util.mock.genMockShowcaseAppUI

data class HomeViewState(
    // Data
    val apps: List<ShowcaseAppUI> = emptyList(),
    val categorySections: List<CategorySection> = emptyList(),

    // States
    val refreshing: Boolean = false,
    val loading: Boolean = false,

    val appInView: ShowcaseAppUI = genMockShowcaseAppUI(),

    val filterButtons: List<FilterButtonData> = emptyList(),
    val activeCountryFilter: Country = Country.Norway
)
