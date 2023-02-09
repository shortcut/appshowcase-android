package io.shortcut.showcase.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import io.shortcut.showcase.data.mapper.GeneralCategory
import io.shortcut.showcase.presentation.common.filter.data.CountryFilter
import io.shortcut.showcase.presentation.common.filter.view.CountryFilterRow
import io.shortcut.showcase.presentation.common.gradient.GradientOverlay
import io.shortcut.showcase.presentation.common.topbar.TopBarWithLogoAndSearch
import io.shortcut.showcase.presentation.data.ShowcaseBannerUI
import io.shortcut.showcase.presentation.home.data.CategorySection
import io.shortcut.showcase.presentation.home.navigation.HomeScreenDestinations
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.util.dimens.Dimens
import io.shortcut.showcase.util.mock.genMockBanners
import io.shortcut.showcase.util.mock.genMockFilterButtons
import io.shortcut.showcase.util.mock.genMockShowcaseAppUIList
import kotlinx.coroutines.delay

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun HomeScreenContentList(
    refreshState: PullRefreshState,
    onNavDestinations: (HomeScreenDestinations) -> Unit,
    homeAppsState: HomeState,
    onSearch: (String) -> Unit,
    onSearchVisible: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(refreshState)
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Scaffold(
            topBar = {
                TopBarWithLogoAndSearch(
                    modifier = Modifier,
                    onLongClick = {
                        onNavDestinations(HomeScreenDestinations.IdleScreen)
                    },
                    onSearch = onSearch,
                    onSearchVisible = onSearchVisible
                )
            },
            modifier = Modifier
                .fillMaxSize()
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                when (homeAppsState) {
                    is HomeState.HomeAppSearchState -> HomeSearchResultList(
                        paddingValues,
                        homeAppsState
                    )

                    is HomeState.HomeAppsGridState -> HomeAppsGrid(paddingValues, homeAppsState)
                }

            }
        }

        GradientOverlay(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .align(Alignment.BottomCenter),
            topColor = ExtendedShowcaseTheme.colors.ShowcaseOverlay,
            bottomColor = Color.Transparent
        )
        if (homeAppsState is HomeState.HomeAppsGridState) {
            PullRefreshIndicator(
                homeAppsState.isRefreshing,
                refreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
private fun HomeAppsGrid(
    paddingValues: PaddingValues,
    homeAppsGridState: HomeState.HomeAppsGridState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ExtendedShowcaseTheme.colors.ShowcaseBackground)
            .padding(paddingValues)
    ) {
        item {
            HomeContent(
                banners = homeAppsGridState.banners,
                filterButtons = homeAppsGridState.filterButtons,
                sections = homeAppsGridState.categorizedApps
            )
        }
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    banners: List<ShowcaseBannerUI>,
    filterButtons: List<CountryFilter>,
    sections: List<CategorySection>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = ExtendedShowcaseTheme.colors.ShowcaseBackground)
    ) {
        HomeScreenPager(images = banners)
        Spacer(modifier = Modifier.height(Dimens.L))
        CountryFilterRow(
            modifier = Modifier.fillMaxWidth(),
            buttons = filterButtons,
            buttonSpacing = Dimens.S,
            horizontalContentPadding = Dimens.S
        )
        Spacer(modifier = Modifier.height(Dimens.L))
        sections.forEach { section ->
            HomeCategoryRow(
                modifier = Modifier
                    .padding(horizontal = Dimens.S),
                rowTitle = section.generalCategory,
                apps = section.apps,
                onShowAllClick = { section.onClickShowAll() }
            )
            Spacer(modifier = Modifier.height(Dimens.fourty))
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HomeScreenPager(
    modifier: Modifier = Modifier,
    images: List<ShowcaseBannerUI>
) {
    val pageCount: Int = images.size
    val startIndex = pageCount / 2

    val pagerState = rememberPagerState(
        initialPage = startIndex
    )

    // Auto scrolls the carousel
    val autoscroll by remember { mutableStateOf(false) }
    LaunchedEffect(autoscroll) {
        while (true) {
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
            delay(3000)
        }
    }

    HorizontalPager(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp),
        count = Int.MAX_VALUE,
        state = pagerState,
    ) { index ->
        val page = (index - startIndex).floorMod(pageCount)

        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = images[page].imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillHeight
        )
    }
}

@Preview
@Composable
private fun HomeScreenPagerPreview() {
    ExtendedShowcaseTheme {
        HomeScreenPager(
            images = genMockBanners()
        )
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    ExtendedShowcaseTheme {
        HomeContent(
            banners = genMockBanners(),
            filterButtons = genMockFilterButtons(),
            sections = emptyList()
        )
    }
}

@Preview
@Composable
private fun HomeCategoryRowPreview() {
    ExtendedShowcaseTheme {
        HomeCategoryRow(
            modifier = Modifier
                .padding(horizontal = Dimens.S),
            rowTitle = GeneralCategory.BUSINESS,
            apps = genMockShowcaseAppUIList()
        )
    }
}

// Helper function to create an infinite loop for the pager
private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}