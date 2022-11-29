package io.shortcut.showcase.data.mapper

import io.shortcut.showcase.data.local.Histogram
import io.shortcut.showcase.data.local.Screenshots
import io.shortcut.showcase.data.local.ShowcaseAppEntity
import io.shortcut.showcase.domain.model.ShowcaseAppAPI
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.util.formatter.formatCountry
import io.shortcut.showcase.util.formatter.formatGeneralCategory

fun ShowcaseAppAPI.toShowcaseAppEntity(): ShowcaseAppEntity {
    return ShowcaseAppEntity(
        genreAndroid = genreAndroid,
        genreIos = genreIos,
        iconAndroid = iconAndroid,
        iconIos = iconIos,
        installsAndroid = installsAndroid,
        isShortcutApp = isShortcutApp,
        packageAndroid = packageAndroid,
        ratingAndroid = ratingAndroid,
        ratingIos = ratingIos,
        releasedAndroid = releasedAndroid,
        releasedIos = releasedIos,
        scoreTextAndroid = scoreTextAndroid,
        scoreTextIos = scoreTextIos,
        summaryAndroid = summaryAndroid,
        summaryIos = summaryIos,
        titleAndroid = titleAndroid,
        titleIos = titleIos,

        screenshots = Screenshots(
            imagesAndroid = imagesAndroid,
            imagesIos = imagesIos
        ),
        histogram = Histogram(
            histogramAndroid = histogramAndroid,
            histogramIos = histogramIos
        ),

        generalCategory = formatGeneralCategory(genreIos),
        country = formatCountry(country)
    )
}

fun ShowcaseAppEntity.toShowcaseAppUI() : ShowcaseAppUI {
    return ShowcaseAppUI(
        id = id,

        genreAndroid = genreAndroid,
        genreIos = genreIos,
        iconAndroid = iconAndroid,
        iconIos = iconIos,
        installsAndroid = installsAndroid,
        isShortcutApp = isShortcutApp,
        packageAndroid = packageAndroid,
        ratingAndroid = ratingAndroid,
        ratingIos = ratingIos,
        releasedAndroid = releasedAndroid,
        releasedIos = releasedIos,
        scoreTextAndroid = scoreTextAndroid,
        scoreTextIos = scoreTextIos,
        summaryAndroid = summaryAndroid,
        summaryIos = summaryIos,
        titleAndroid = titleAndroid,
        titleIos = titleIos,

        screenshots = screenshots,
        histogram = histogram,

        generalCategory = generalCategory,
        country = country
    )
}
