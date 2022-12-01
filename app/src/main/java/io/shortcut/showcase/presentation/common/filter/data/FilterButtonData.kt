package io.shortcut.showcase.presentation.common.filter.data

import io.shortcut.showcase.data.mapper.Country

data class FilterButtonData(
    val type: Country,
    val selected: Boolean,
    val onClick: () -> Unit
)
