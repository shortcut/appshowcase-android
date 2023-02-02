package io.shortcut.showcase.presentation.showAll

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.presentation.common.filter.data.CountryFilter
import io.shortcut.showcase.presentation.common.filter.view.CountryFilterRow
import io.shortcut.showcase.presentation.home.navigation.HomeScreenDestinations
import io.shortcut.showcase.presentation.home.view.AppListWithBottomSheetLayout
import io.shortcut.showcase.presentation.home.view.BottomSheetContentEvents
import io.shortcut.showcase.presentation.home.view.CategoryRowItem
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import io.shortcut.showcase.util.extensions.ViewEffects
import io.shortcut.showcase.util.mock.genMockShowcaseAppUIList
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ShowAllScreen(
    onBackClick: () -> Unit,
    showAllViewModel: ShowAllViewModel = hiltViewModel(),
    onNavDestinations: (HomeScreenDestinations) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = ShowcaseThemeCustom.colors.ShowcaseBackground)
    systemUiController.isNavigationBarVisible = false
    val state by showAllViewModel.showAppListSState.collectAsState()

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    AppListWithBottomSheetLayout(
        currentContent = state.bottomSheet,
        onEvent = { events ->
            when (events) {
                is BottomSheetContentEvents.Dismiss -> showAllViewModel.dismissBottomSheet()
                is BottomSheetContentEvents.Gallery -> onNavDestinations(
                    HomeScreenDestinations.ScreenshotGallery(
                        imageIndex = events.startIndex,
                        imageUrls = events.list
                    )
                )

                is BottomSheetContentEvents.SortListBy -> {
                    showAllViewModel.sortListBy(events.sortBy)
                }

                is BottomSheetContentEvents.ShowCategoryFilter -> {
                    showAllViewModel.filterByCategory(
                        events.category
                    )
                }
            }
        },
        modalBottomSheetState = modalBottomSheetState
    ) {
        Scaffold(
            contentColor = Color.White,
            containerColor = ShowcaseThemeCustom.colors.ShowcaseBackground,
            topBar = {
                ToolBarWithSearch(onBackClick, scrollBehavior)
            },
            modifier = Modifier
                .background(ShowcaseThemeCustom.colors.ShowcaseBackground),
        ) {
            ShowAllScreenContent(
                state = state,
                modifier = Modifier.padding(it),
                viewModel = showAllViewModel
            )
        }
    }

    ViewEffects(viewEffects = showAllViewModel.viewEffects) { event ->
        when (event) {
            is ShowAllAppEvent.ShowBottomSheet -> launch {
                modalBottomSheetState.show()
            }

            is ShowAllAppEvent.DismissBottomSheet -> modalBottomSheetState.hide()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ToolBarWithSearch(
    onBackClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {},
        navigationIcon = { NavigationIcon(onBackClick) },
        actions = {
            AboutActionIcon()
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = ShowcaseThemeCustom.colors.ShowcaseBackground,
            scrolledContainerColor = ShowcaseThemeCustom.colors.ShowcaseBackground,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun ShowAllScreenContent(
    state: ShowAllAppsState,
    modifier: Modifier = Modifier,
    viewModel: ShowAllViewModel? = null
) {
    Column(modifier = modifier) {
        CountryFilterRow(buttons = state.countryFilter, modifier = Modifier.height(68.dp))
        SortAndCategoryFilters(
            onSort = {
                viewModel?.openSortOrder()
            },
            onFilter = {
                viewModel?.openCategoryFilter()
            },
            modifier = Modifier.fillMaxWidth(),
            selectedFilter = state.selectedCategory
        )
        LazyHorizontalGrid(
            modifier = Modifier
                .fillMaxSize(),
            rows = GridCells.Adaptive(164.dp),
            contentPadding = PaddingValues(start = 14.dp, end = 14.dp)
        ) {
            items(state.apps) { app ->
                CategoryRowItem(
                    imageURL = app.iconUrl,
                    appTitle = app.title,
                    appRating = app.highestRating,
                    appIconSize = 80.dp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                    onAppIconClick = app.onClick,
                    showInstallationInfo = true
                )
            }
        }
    }
}

@Composable
fun NavigationIcon(onBackClick: () -> Unit) {
    IconButton(
        onClick = { onBackClick() },
        colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null
        )
    }
}

@Composable
fun AboutActionIcon() {
    IconButton(
        onClick = { }
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null
        )
    }
}

@Composable
@Preview
fun ShowAllScreenPreview() {
    ExtendedShowcaseTheme {
        ShowAllScreenContent(
            state = ShowAllAppsState(
                countryFilter = CountryFilter.getCountryFilterList(
                    Country.Denmark
                ) {
                },
                apps = genMockShowcaseAppUIList()
            )
        )
    }
}