package io.shortcut.showcase.data.mapper

enum class SortOrder {
    Priority,
    Alphabetical,
    Rating,
    Popularity
}

fun getAllSortingOrders() = SortOrder.values().toList()