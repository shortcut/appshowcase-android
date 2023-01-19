package io.shortcut.showcase.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.shortcut.showcase.data.mapper.GeneralCategory
import io.shortcut.showcase.presentation.common.ModularBottomSheet
import io.shortcut.showcase.presentation.common.TopBar
import io.shortcut.showcase.presentation.common.filter.data.FilterButtonData
import io.shortcut.showcase.presentation.common.filter.view.FilterRow
import io.shortcut.showcase.presentation.common.gradient.GradientOverlay
import io.shortcut.showcase.presentation.data.ShowcaseBannerUI
import io.shortcut.showcase.presentation.home.data.CategorySection
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import io.shortcut.showcase.util.dimens.Dimens
import io.shortcut.showcase.util.extensions.ViewEffects
import io.shortcut.showcase.util.mock.genMockBanners
import io.shortcut.showcase.util.mock.genMockFilterButtons
import io.shortcut.showcase.util.mock.genMockShowcaseAppUIList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onIdleClick: () -> Unit,
    onScreenshotClick: (Int, List<String>) -> Unit
) {
    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = ShowcaseThemeCustom.colors.ShowcaseBackground)
    systemUiController.isNavigationBarVisible = false

    val homeViewState: HomeViewState by viewModel.homeViewState.collectAsState()
    val refreshState =
        rememberPullRefreshState(refreshing = homeViewState.refreshing, onRefresh = {
            viewModel.refreshAppsList()
        })

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false
    )

    val appInView = homeViewState.appInView

    ViewEffects(viewEffects = viewModel.viewEffects) {
        when (it) {
            HomeViewEffect.OpenBottomSheet -> launch {
                modalBottomSheetState.animateTo(
                    ModalBottomSheetValue.HalfExpanded
                )
            }

            HomeViewEffect.HideBottomSheet -> launch {
                modalBottomSheetState.animateTo(
                    ModalBottomSheetValue.Hidden
                )
            }

            is HomeViewEffect.NavigateToGallery -> {
                onScreenshotClick(it.startIndex, it.imageList)
            }
        }
    }

    ModularBottomSheet(
        state = modalBottomSheetState,
        sheetBackgroundColor = ShowcaseThemeCustom.colors.ShowcaseBackground,
        sheetContent = {
            HomeSheetContent(
                childModifier = Modifier
                    .padding(horizontal = Dimens.M),
                app = appInView,
                onScreenshotClick = onScreenshotClick
            )
        },
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
                    TopBar(
                        modifier = Modifier
                            .padding(
                                horizontal = Dimens.S,
                                vertical = Dimens.M
                            ),
                        color = ShowcaseThemeCustom.colors.ShowcaseBackground,
                        iconTint = ShowcaseThemeCustom.colors.ShowcaseSecondary,
                        onLongClick = {
                            onIdleClick()
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = ShowcaseThemeCustom.colors.ShowcaseBackground)
                        .padding(paddingValues)
                ) {
                    item {
                        HomeContent(
                            banners = homeViewState.banners,
                            filterButtons = homeViewState.filterButtons,
                            sections = homeViewState.categorizedApps
                        )
                    }
                }
            }

            GradientOverlay(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f)
                    .align(Alignment.BottomCenter),
                topColor = ShowcaseThemeCustom.colors.ShowcaseOverlay,
                bottomColor = Color.Transparent
            )
            PullRefreshIndicator(
                homeViewState.refreshing,
                refreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    banners: List<ShowcaseBannerUI>,
    filterButtons: List<FilterButtonData>,
    sections: List<CategorySection>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = ShowcaseThemeCustom.colors.ShowcaseBackground)
    ) {
        HomeScreenPager(images = banners)
        Spacer(modifier = Modifier.height(Dimens.L))
        FilterRow(
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
    var autoscroll by remember { mutableStateOf(false) }
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