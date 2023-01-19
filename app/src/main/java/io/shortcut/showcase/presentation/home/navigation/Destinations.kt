package io.shortcut.showcase.presentation.home.navigation

import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.data.mapper.GeneralCategory

sealed class ShowcaseDestination(val route: String) {
    object Home : ShowcaseDestination(route = "home")
    object Idle : ShowcaseDestination(route = "idle")
    object ShowAllApps :
        ShowcaseDestination(route = "show_all_apps/?country={country}&category={category}")

    object ScreenshotGallery :
        ShowcaseDestination(route = "screenshot_gallery/?startIndex={startIndex}&imageList={imageList}")
}


sealed class HomeScreenDestinations {
    object IdleScreen : HomeScreenDestinations()
    data class ScreenshotGallery(val imageUrls: List<String>, val imageIndex: Int) :
        HomeScreenDestinations()

    data class ShowAllAppsScreen(val country: Country, val category: GeneralCategory) :
        HomeScreenDestinations()
}