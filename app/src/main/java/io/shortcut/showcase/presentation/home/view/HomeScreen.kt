package io.shortcut.showcase.presentation.home.view

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.shortcut.showcase.presentation.home.navigation.HomeScreenDestinations
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.util.extensions.ViewEffects
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavDestinations: (HomeScreenDestinations) -> Unit
) {
    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = ExtendedShowcaseTheme.colors.ShowcaseBackground)
    systemUiController.isNavigationBarVisible = false

    val homeViewState: HomeViewState by viewModel.homeViewState.collectAsState()
    val refreshState =
        rememberPullRefreshState(refreshing = homeViewState.refreshing, onRefresh = {
            viewModel.refreshAppsList()
        })

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    ViewEffects(viewEffects = viewModel.viewEffects) {
        when (it) {
            is HomeViewEffect.OpenBottomSheet -> launch {
                modalBottomSheetState.show()
            }

            HomeViewEffect.HideBottomSheet -> launch {
                modalBottomSheetState.hide()
            }

            is HomeViewEffect.NavigateToGallery -> {
                onNavDestinations(
                    HomeScreenDestinations.ScreenshotGallery(
                        imageIndex = it.startIndex,
                        imageUrls = it.imageList
                    )
                )
            }

            is HomeViewEffect.NavigateToShowAllApps -> onNavDestinations(
                HomeScreenDestinations.ShowAllAppsScreen(
                    country = it.country,
                    category = it.category
                )
            )
        }
    }
    AppListWithBottomSheetLayout(
        currentContent = homeViewState.bottomSheet,
        onEvent = { events ->
            when (events) {
                is BottomSheetContentEvents.Dismiss -> viewModel.hideBottomSheet()
                is BottomSheetContentEvents.Gallery -> onNavDestinations(
                    HomeScreenDestinations.ScreenshotGallery(
                        imageIndex = events.startIndex,
                        imageUrls = events.list
                    )
                )

                else -> {
                }
            }
        },
        modalBottomSheetState = modalBottomSheetState
    ) {
        HomeScreenContentList(refreshState, onNavDestinations, homeViewState)
    }
}