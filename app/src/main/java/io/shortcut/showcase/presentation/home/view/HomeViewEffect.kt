package io.shortcut.showcase.presentation.home.view

import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.data.mapper.GeneralCategory

sealed class HomeViewEffect {
    object OpenBottomSheet : HomeViewEffect()
    object HideBottomSheet : HomeViewEffect()
    data class NavigateToShowAllApps(val country: Country, val category: GeneralCategory) :
        HomeViewEffect()

    data class NavigateToGallery(val startIndex: Int, val imageList: List<String>) :
        HomeViewEffect()
}
