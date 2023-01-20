package io.shortcut.showcase.presentation.showAll

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import io.shortcut.showcase.presentation.common.filter.data.CountryFilter
import io.shortcut.showcase.presentation.common.filter.view.FilterRow
import io.shortcut.showcase.presentation.home.view.CategoryRowItem
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import io.shortcut.showcase.util.dimens.Dimens
import io.shortcut.showcase.util.mock.genMockShowcaseAppUIList

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowAllScreen(
    onBackClick: () -> Boolean,
    showAllViewModel: ShowAllViewModel = hiltViewModel()
) {
    val stateTopBar = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = ShowcaseThemeCustom.colors.ShowcaseBackground)
    systemUiController.isNavigationBarVisible = false
    val state by showAllViewModel.showAppListSState.collectAsState()
    Scaffold(
        contentColor = Color.White,
        containerColor = ShowcaseThemeCustom.colors.ShowcaseBackground,
        topBar = {
            TopAppBar(
                title = {

                },
                navigationIcon = { NavigationIcon() },
                actions = {
                    AboutActionIcon()
                }, colors = TopAppBarDefaults.mediumTopAppBarColors(
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
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = Dimens.S, end = Dimens.S),
                columns = GridCells.Fixed(3),
            ) {
                items(state.apps) { app ->
                    CategoryRowItem(
                        imageURL = app.iconUrl,
                        appTitle = app.title,
                        appRating = app.highestRating,
                        appIconSize = 80.dp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
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
fun NavigationIcon() {
    IconButton(
        onClick = { }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null
        )
    }
}

@Composable
fun AboutActionIcon() {
    IconButton(
        onClick = { }) {
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