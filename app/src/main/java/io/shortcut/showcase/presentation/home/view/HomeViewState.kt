package io.shortcut.showcase.presentation.home.view

import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.presentation.home.data.CategorySection

data class HomeViewState(
    // Data
    val apps: List<ShowcaseAppUI> = emptyList(),
    val categorySections: List<CategorySection> = emptyList(),

    // States
    val refreshing: Boolean = false,
    val loading: Boolean = false,
)
