package io.shortcut.showcase.presentation.home.view

sealed class HomeViewEffects {
    object OpenBottomSheet: HomeViewEffects()
    object HideBottomSheet: HomeViewEffects()
}
