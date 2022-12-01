package io.shortcut.showcase.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.shortcut.showcase.util.dimens.Dimens

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModularBottomSheet(
    state: ModalBottomSheetState,
    modifier: Modifier = Modifier,
    sheetBackgroundColor: Color,
    sheetContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = state,
        modifier = modifier,
        sheetShape = RoundedCornerShape(
            topStart = Dimens.M,
            topEnd = Dimens.M,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        sheetBackgroundColor = sheetBackgroundColor,
        sheetContent = {
            Column(
                modifier = Modifier
                    .defaultMinSize(minHeight = 1.dp) // https://stackoverflow.com/q/68623965
            ) {
                sheetContent()
            }
        },
        content = content,
    )
}