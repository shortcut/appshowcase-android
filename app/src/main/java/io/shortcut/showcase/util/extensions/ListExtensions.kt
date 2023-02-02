package io.shortcut.showcase.util.extensions

import io.shortcut.showcase.data.mapper.SortOrder
import io.shortcut.showcase.presentation.data.ShowcaseAppUI

fun List<ShowcaseAppUI>.sortAppsList(
    sortBy: SortOrder
): List<ShowcaseAppUI> {
    fun sortingTypes(
        sortBy: SortOrder,
        app: ShowcaseAppUI
    ) = when (sortBy) {
        SortOrder.Alphabetical -> app.title
        SortOrder.Rating -> app.highestRating
        SortOrder.Popularity -> app.totalInstalls
        SortOrder.Priority -> app.country.name
    }

    val orderDescending = when (sortBy) {
        SortOrder.Priority, SortOrder.Alphabetical -> false
        else -> true
    }
    return if (orderDescending) {
        this.sortedByDescending {
            sortingTypes(sortBy, it)
        }
    } else {
        this.sortedBy {
            sortingTypes(sortBy, it)
        }
    }
}