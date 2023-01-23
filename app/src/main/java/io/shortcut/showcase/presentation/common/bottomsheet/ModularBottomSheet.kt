package io.shortcut.showcase.presentation.common.bottomsheet

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.shortcut.showcase.util.dimens.Dimens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModularBottomSheet(
    state: ModalBottomSheetState,
    modifier: Modifier = Modifier,
    sheetBackgroundColor: Color,
    sheetContent: @Composable ColumnScope.(ModalBottomSheetValue) -> Unit,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val isExpanded = state.currentValue == ModalBottomSheetValue.Expanded
    ModalBottomSheetLayout(
        sheetState = state,
        modifier = modifier,
        sheetShape = RoundedCornerShape(
            topStart = if (isExpanded) 0.dp else Dimens.M,
            topEnd = if (isExpanded) 0.dp else Dimens.M,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        sheetBackgroundColor = sheetBackgroundColor,
        sheetContent = {
            Column(
                modifier = Modifier
                    .defaultMinSize(minHeight = 1.dp) // https://stackoverflow.com/q/68623965
            ) {
                sheetContent(state.currentValue)
            }
        },
        content = content,
        sheetElevation = Dimens.S
    )

    BackHandler {
        coroutineScope.launch {
            if (state.isVisible) {
                state.hide() // will trigger the LaunchedEffect
            }
        }
    }
}