package io.shortcut.showcase.util.validate

import io.shortcut.showcase.domain.model.ShowcaseAppAPI


fun showcaseAppAPIContainsNull(app: ShowcaseAppAPI?): Boolean {
    if (app == null) {
        return true
    }
    if (app.title.isNullOrBlank()) {
        return true
    }
    if (app.iconUrl.isNullOrBlank()) {
        return true
    }
    if (app.publisher.isNullOrBlank()) {
        return true
    }
    if (app.screenshots.isNullOrEmpty()) {
        return true
    }
    if (app.totalInstalls.isNullOrBlank()) {
        return true
    }
    if (app.shortDescription.isNullOrBlank()) {
        return true
    }

    if (app.generalCategory.isNullOrBlank()) {
        return true
    }

    if (app.highestRating.isNullOrBlank()) {
        return true
    }

    /*if (app.totalHistogram.isNullOrEmpty()) {
        return true
    }*/

    if (app.androidPackageID.isNullOrBlank()) {
        return true
    }

    return false
}
