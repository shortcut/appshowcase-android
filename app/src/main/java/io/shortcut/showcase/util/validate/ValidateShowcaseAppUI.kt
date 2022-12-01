package io.shortcut.showcase.util.validate

import io.shortcut.showcase.presentation.data.ShowcaseAppUI


fun validateShowcaseAppUI(app: ShowcaseAppUI): Boolean {
    if (app.iconIos.isBlank() && app.iconAndroid.isBlank()) {
        return false
    }

    if (app.titleIos.isBlank() && app.titleIos.isBlank()) {
        return false
    }

    if (app.country.name.isBlank()) {
        return false
    }

    if (app.scoreTextAndroid.isBlank()) {
        return false
    }

    if (app.installsAndroid.isBlank()) {
        return false
    }

    if (app.summaryIos.isBlank()) {
        return false
    }

    if (app.screenshots.imagesIos.isEmpty()) {
        return false
    }

    return true
}
