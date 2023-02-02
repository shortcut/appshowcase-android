package io.shortcut.showcase.presentation.showAll

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.shortcut.showcase.data.mapper.GeneralCategory
import io.shortcut.showcase.data.mapper.getAllCategories
import io.shortcut.showcase.data.mapper.getCategoryEnumFromStringValue
import io.shortcut.showcase.presentation.common.VerticalRadioButtonGroup
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom

@Composable
fun AppListCategories(
    modifier: Modifier = Modifier,
    onSelectedCategory: (GeneralCategory) -> Unit
) {
    Column(modifier = modifier.padding(horizontal = 40.dp)) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "Category", style = ShowcaseThemeCustom.typography.h1, color = Color.White)
        Spacer(modifier = Modifier.height(32.dp))
        VerticalRadioButtonGroup(radioOptions = getAllCategories(), onOption = {
            onSelectedCategory(getCategoryEnumFromStringValue(value = it))
        })
        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
@Preview
private fun AppListCategoriesPreview() {
    AppListCategories {
    }
}