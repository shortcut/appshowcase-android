package io.shortcut.showcase.data.mapper

import io.shortcut.showcase.domain.model.ShowcaseBannerAPI
import io.shortcut.showcase.presentation.data.ShowcaseBannerUI

fun ShowcaseBannerAPI.toShowcaseBannerUI(): ShowcaseBannerUI {
    return ShowcaseBannerUI(
        id = id,
        imageUrl = imageUrl
    )
}