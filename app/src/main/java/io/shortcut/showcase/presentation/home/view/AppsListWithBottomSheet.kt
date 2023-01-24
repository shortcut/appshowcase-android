package io.shortcut.showcase.presentation.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.shortcut.showcase.presentation.common.bottomsheet.ModularBottomSheet
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

sealed class BottomSheetContent {
    class AppInformation(val appInSheet: ShowcaseAppUI) : BottomSheetContent()
    object SortOrder : BottomSheetContent()
    object CategoryFilter : BottomSheetContent()
}

sealed class SheetState {
    class Open(val sheetContent: BottomSheetContent) : SheetState()
    object Close : SheetState()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppListWithBottomSheetLayout(mainContent: @Composable (MutableSharedFlow<SheetState>) -> Unit) {
    val scope = rememberCoroutineScope()
    val sheetState: MutableSharedFlow<SheetState> = MutableSharedFlow(0, 100)
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false
    )
    var currentBottomSheet: BottomSheetContent? by remember {
        mutableStateOf(null)
    }

    val closeSheet: () -> Unit = {
        scope.launch {
            modalBottomSheetState.hide()
        }
    }

    val openSheet: (BottomSheetContent) -> Unit = {
        currentBottomSheet = it
        scope.launch { modalBottomSheetState.show() }
    }
    if (!modalBottomSheetState.isVisible) {
        currentBottomSheet = null
    }
    ModularBottomSheet(
        state = modalBottomSheetState,
        sheetBackgroundColor = ShowcaseThemeCustom.colors.ShowcaseBackground,
        sheetContent = {
            currentBottomSheet?.let { currentSheet ->
                SheetLayout(currentSheet, onCloseBottomSheet = {
                    sheetState.tryEmit(SheetState.Close)
                }, modalBottomSheetState)
            }
        },
    ) {
        mainContent(sheetState)
        LaunchedEffect(key1 = sheetState) {
            sheetState.collect {
                when (it) {
                    is SheetState.Close -> closeSheet()
                    is SheetState.Open -> openSheet(it.sheetContent)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetLayout(
    currentScreen: BottomSheetContent,
    onCloseBottomSheet: () -> Unit,
    modalBottomSheetState: ModalBottomSheetState
) {
    Box(Modifier.fillMaxWidth()) {
        when (currentScreen) {
            is BottomSheetContent.CategoryFilter -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            is BottomSheetContent.SortOrder -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            is BottomSheetContent.AppInformation -> HomeSheetContent(
                modifier = Modifier,
                app = currentScreen.appInSheet,
                onScreenshotClick = { startIndex, list ->
                    /*onNavDestinations(
                        HomeScreenDestinations.ScreenshotGallery(
                            imageIndex = startIndex,
                            imageUrls = list
                        )
                    )*/
                },
                sheetState = modalBottomSheetState.currentValue,
                onBackClick = {
                    onCloseBottomSheet()
                }
            )
        }
    }
}
