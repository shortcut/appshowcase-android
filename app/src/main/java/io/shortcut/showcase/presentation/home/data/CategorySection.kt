package io.shortcut.showcase.presentation.home.data

import io.shortcut.showcase.data.mapper.GeneralCategory
import io.shortcut.showcase.presentation.data.ShowcaseAppUI

data class CategorySection(
    val generalCategory: GeneralCategory,
    val apps: List<ShowcaseAppUI>,
    val onClickShowAll: () -> Unit
)