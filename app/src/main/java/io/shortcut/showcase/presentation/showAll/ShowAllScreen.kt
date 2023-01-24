package io.shortcut.showcase.presentation.showAll

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.presentation.common.bottomsheet.ModularBottomSheet
import io.shortcut.showcase.presentation.common.filter.data.CountryFilter
import io.shortcut.showcase.presentation.common.filter.view.FilterRow
import io.shortcut.showcase.presentation.home.navigation.HomeScreenDestinations
import io.shortcut.showcase.presentation.home.view.CategoryRowItem
import io.shortcut.showcase.presentation.home.view.HomeSheetContent
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import io.shortcut.showcase.util.dimens.Dimens
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
        skipHalfExpanded = false
    )

    val appSelectedForBottomSheet = state.appSelectedForBottomSheet
    ModularBottomSheet(
        state = modalBottomSheetState,
        sheetBackgroundColor = ShowcaseThemeCustom.colors.ShowcaseBackground,
        sheetContent = {
            HomeSheetContent(
                modifier = Modifier,
                app = appSelectedForBottomSheet,
                onScreenshotClick = { startIndex, list ->
                    onNavDestinations(
                        HomeScreenDestinations.ScreenshotGallery(
                            imageIndex = startIndex,
                            imageUrls = list
                        )
                    )
                },
                sheetState = it,
                onBackClick = {
                    showAllViewModel.dismissAppInformation()
                }
            )
        },
    ) {
        Scaffold(
            contentColor = Color.White,
            containerColor = ShowcaseThemeCustom.colors.ShowcaseBackground,
            topBar = {
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
            },
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .background(ShowcaseThemeCustom.colors.ShowcaseBackground),
        ) {
            Column(modifier = Modifier.padding(it)) {
                FilterRow(buttons = state.countryFilter, modifier = Modifier.height(68.dp))
                LazyHorizontalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    rows = GridCells.Adaptive(154.dp),
                    contentPadding = PaddingValues(start = 14.dp, end = 14.dp)
                ) {
                    items(state.apps) { app ->
                        CategoryRowItem(
                            imageURL = app.iconUrl,
                            appTitle = app.title,
                            appRating = app.highestRating,
                            appIconSize = 80.dp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                            onAppIconClick = app.onClick
                        )
                    }
                }
            }
        }
    }

    ViewEffects(viewEffects = showAllViewModel.viewEffects) { event ->
        when (event) {
            is ShowAllAppEvent.ShowAppInformation -> launch {
                modalBottomSheetState.animateTo(ModalBottomSheetValue.HalfExpanded)
            }

            is ShowAllAppEvent.DismissAppInformation -> modalBottomSheetState.animateTo(
                ModalBottomSheetValue.Hidden
            )
        }
    }
}

@Composable
private fun ShowAllScreenContent(state: ShowAllAppsState) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(Dimens.M))
        FilterRow(buttons = state.countryFilter)
        Spacer(modifier = Modifier.height(Dimens.S))
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