package io.shortcut.showcase.presentation.showAll

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.shortcut.showcase.R
import io.shortcut.showcase.data.mapper.SortOrder
import io.shortcut.showcase.data.mapper.getAllSortingOrders
import io.shortcut.showcase.presentation.common.VerticalRadioButtonGroup
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme

@Composable
fun AppListSortBy(
    modifier: Modifier = Modifier,
    onSelectedSortOrder: (SortOrder) -> Unit,
    selectedSortOrder: SortOrder
) {
    Column(modifier = modifier.padding(horizontal = 40.dp)) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = stringResource(R.string.title_sort_by),
            style = ExtendedShowcaseTheme.typography.h1,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(32.dp))
        VerticalRadioButtonGroup(radioOptions = getAllSortingOrders().map { it.name }, onOption = {
            onSelectedSortOrder(SortOrder.valueOf(it))
        }, selectedOption = selectedSortOrder.name)
        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
@Preview
private fun AppListSortByPreview() {
    AppListSortBy(onSelectedSortOrder = {}, selectedSortOrder = SortOrder.Rating)
}