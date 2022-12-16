package io.shortcut.showcase.presentation.home.view

sealed class HomeViewEffect {
    object OpenBottomSheet : HomeViewEffect()
    object HideBottomSheet : HomeViewEffect()

    data class naviateToGallery(val startIndex: Int) : HomeViewEffect()
}
