package io.shortcut.showcase.data.mapper

import io.shortcut.showcase.data.local.Screenshots
import io.shortcut.showcase.data.local.ShowcaseAppEntity
import io.shortcut.showcase.domain.model.ShowcaseAppAPI
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.util.formatter.formatCountry
import io.shortcut.showcase.util.formatter.formatGeneralCategory

fun ShowcaseAppAPI.toShowcaseAppEntity(): ShowcaseAppEntity {
    return ShowcaseAppEntity(
        id = 0,

        title = title ?: "Unknown",
        iconUrl = iconUrl ?: "Unknown",
        publisher = publisher ?: "Unknown",
        country = country?.let { formatCountry(it) } ?: Country.Unknown,
        screenshots = screenshots?.let { Screenshots(imageURLs = it) } ?: Screenshots(emptyList()),
        totalInstalls = totalInstalls ?: "Unknown",
        shortDescription = shortDescription ?: "Unknown",
        generalCategory = generalCategory?.let { formatGeneralCategory(it) } ?: GeneralCategory.OTHER,
        highestRating = highestRating?.toFloat() ?: 0.0f,
        // totalHistogram = Histogram(histogramData = totalHistogram),
        androidPackageID = androidPackageID ?: "Unknown"
    )
}

fun ShowcaseAppEntity.toShowcaseAppUI(): ShowcaseAppUI {
    return ShowcaseAppUI(
        id = id,

        title = title,
        iconUrl = iconUrl,
        publisher = publisher,
        country = country,
        screenshots = screenshots,
        totalInstalls = totalInstalls,
        shortDescription = shortDescription,
        generalCategory = generalCategory,
        highestRating = highestRating.toString(),
        // totalHistogram = totalHistogram,
        androidPackageID = androidPackageID
    )
}
