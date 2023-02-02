package io.shortcut.showcase.presentation.home.view

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.shortcut.showcase.data.mapper.GeneralCategory
import io.shortcut.showcase.data.mapper.SortOrder
import io.shortcut.showcase.presentation.common.bottomsheet.ModularBottomSheet
import io.shortcut.showcase.presentation.showAll.AppListCategories
import io.shortcut.showcase.presentation.showAll.AppListSortBy
import io.shortcut.showcase.presentation.showAll.SheetContent
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import io.shortcut.showcase.util.mock.genMockShowcaseAppUI

sealed class BottomSheetContentEvents {
    data class Gallery(val startIndex: Int, val list: List<String>) : BottomSheetContentEvents()
    data class SortListBy(val sortBy: SortOrder) : BottomSheetContentEvents()
    object Dismiss : BottomSheetContentEvents()

    data class ShowCategoryFilter(val category: GeneralCategory) : BottomSheetContentEvents()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppListWithBottomSheetLayout(
    currentContent: SheetContent,
    onEvent: (BottomSheetContentEvents) -> Unit,
    modalBottomSheetState: ModalBottomSheetState,
    mainContent: @Composable () -> Unit
) {
    ModularBottomSheet(
        state = modalBottomSheetState,
        sheetBackgroundColor = ShowcaseThemeCustom.colors.ShowcaseBackground,
        sheetContent = {
            SheetLayout(bottomSheetContent = currentContent, onEvent = onEvent)
        },
    ) {
        mainContent()
    }
}

@Composable
fun SheetLayout(
    bottomSheetContent: SheetContent,
    onEvent: (BottomSheetContentEvents) -> Unit
) {
    when (bottomSheetContent) {
        is SheetContent.AppInfo -> {
            HomeSheetContent(
                modifier = Modifier,
                app = bottomSheetContent.app,
                onScreenshotClick = { startIndex, list ->
                    onEvent(BottomSheetContentEvents.Gallery(startIndex, list))
                },
                onBackClick = {
                    onEvent(BottomSheetContentEvents.Dismiss)
                }
            )
        }

        SheetContent.None -> {
            HomeSheetContent(
                modifier = Modifier,
                app = genMockShowcaseAppUI(),
                onScreenshotClick = { startIndex, list ->
                    onEvent(BottomSheetContentEvents.Gallery(startIndex, list))
                },
                onBackClick = {
                    onEvent(BottomSheetContentEvents.Dismiss)
                }
            )
        }

        SheetContent.Sort -> {
            AppListSortBy(onSelectedSortOrder = {
                onEvent(BottomSheetContentEvents.SortListBy(it))
            })
        }

        SheetContent.Category -> {
            AppListCategories(onSelectedCategory = {
                onEvent(BottomSheetContentEvents.ShowCategoryFilter(it))
            })
        }
    }
}
