package io.shortcut.showcase.presentation.home.navigation

interface ShowcaseDestination {
    val route: String
}

object Home: ShowcaseDestination {
    override val route = "home"
}

object Idle: ShowcaseDestination {
    override val route = "idle"
}

object ScreenshotGallery: ShowcaseDestination {
    override val route = "screenshot_gallery/{startIndex}"
}

