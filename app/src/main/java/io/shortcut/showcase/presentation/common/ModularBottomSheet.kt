package io.shortcut.showcase.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModularBottomSheet(
    state: ModalBottomSheetState,
    modifier: Modifier = Modifier,
    sheetContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = state,
        modifier = modifier,
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

/*
This fixes the bug with the screen being grayed out.
scrimColor = if (VippsTheme.colors.isLight) {
    VippsTheme.colors.onSurface.copy(alpha = 0.32f)
} else {
    VippsTheme.colors.surface.copy(alpha = 0.5f)
}
*/