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
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.shortcut.showcase.data.mapper.GeneralCategory
import io.shortcut.showcase.presentation.common.ModularBottomSheet
import io.shortcut.showcase.presentation.common.TopBar
import io.shortcut.showcase.presentation.common.filter.data.FilterButtonData
import io.shortcut.showcase.presentation.common.filter.view.FilterRow
import io.shortcut.showcase.presentation.common.gradient.GradientOverlay
import io.shortcut.showcase.presentation.home.data.CategorySection
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import io.shortcut.showcase.util.dimens.Dimens
import io.shortcut.showcase.util.mock.genMockBanners
import io.shortcut.showcase.util.mock.genMockFilterButtons
import io.shortcut.showcase.util.mock.genMockShowcaseAppUIList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = ShowcaseThemeCustom.colors.ShowcaseBackground)
    systemUiController.isNavigationBarVisible = false

    val homeViewState: HomeViewState = viewModel.homeViewState
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = homeViewState.refreshing)

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    var appInView = homeViewState.appInView

    ViewEffects(viewEffects = viewModel.viewEffects) {
        when (it) {
            HomeViewEffects.OpenBottomSheet -> launch { modalBottomSheetState.show() }
            HomeViewEffects.HideBottomSheet -> launch { modalBottomSheetState.hide() }
        }
    }

    ModularBottomSheet(
        state = modalBottomSheetState,
        sheetBackgroundColor = ShowcaseThemeCustom.colors.ShowcaseBackground,
        sheetContent = {
            HomeSheetContent(
                childModifier = Modifier
                    .padding(horizontal = Dimens.M),
                app = appInView
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            contentAlignment = Alignment.Center
        ) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { viewModel.fetchDataFromRemote() }
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
                            iconTint = ShowcaseThemeCustom.colors.ShowcaseSecondary
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
                                filterButtons = homeViewState.filterButtons,
                                sections = homeViewState.categorySections
                            )
                        }
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
        }
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    filterButtons: List<FilterButtonData>,
    sections: List<CategorySection>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = ShowcaseThemeCustom.colors.ShowcaseBackground)
    ) {
        HomeScreenPager(images = genMockBanners())
        Spacer(modifier = Modifier.height(Dimens.L))
        FilterRow(
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
            Spacer(modifier = Modifier.height(Dimens.L))
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HomeScreenPager(
    modifier: Modifier = Modifier,
    images: List<String>,
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
            model = images[page],
            contentDescription = null,
            contentScale = ContentScale.FillWidth
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

// Helper function for ViewEffects
@Composable
fun <T> ViewEffects(
    viewEffects: SharedFlow<T>,
    block: suspend CoroutineScope.(T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewEffects.collect { block(it) }
    }
}