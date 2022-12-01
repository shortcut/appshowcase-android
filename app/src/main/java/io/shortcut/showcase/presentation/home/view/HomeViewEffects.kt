package io.shortcut.showcase.presentation.home.view

import io.shortcut.showcase.presentation.data.ShowcaseAppUI

sealed class HomeViewEffects {
    object OpenBottomSheet: HomeViewEffects()
    object HideBottomSheet: HomeViewEffects()

    object StartIdleTimer: HomeViewEffects()
    object ResetIdleTimer: HomeViewEffects()
}
