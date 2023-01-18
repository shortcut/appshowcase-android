package io.shortcut.showcase.presentation.home.view

sealed class HomeViewEffect {
    object OpenBottomSheet : HomeViewEffect()
    object HideBottomSheet : HomeViewEffect()

    data class NavigateToGallery(val startIndex: Int, val imageList: List<String>) :
        HomeViewEffect()
}
