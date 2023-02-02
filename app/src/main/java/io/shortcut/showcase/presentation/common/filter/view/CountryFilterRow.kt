package io.shortcut.showcase.presentation.common.filter.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.shortcut.showcase.presentation.common.filter.data.CountryFilter
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import io.shortcut.showcase.util.dimens.Dimens
import io.shortcut.showcase.util.mock.genMockFilterButtons

@Composable
fun CountryFilterRow(
    modifier: Modifier = Modifier,
    buttons: List<CountryFilter>,
    buttonSpacing: Dp = Dimens.S,
    horizontalContentPadding: Dp = Dimens.S
) {
    Box(modifier = modifier) {
        val lazyListState = rememberLazyListState()
        LazyRow(
            modifier = Modifier.align(alignment = Alignment.Center),
            state = lazyListState,
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = horizontalContentPadding)
        ) {
            items(buttons) { button ->
                FilterButton(
                    text = button.type.name,
                    color = if (button.selected) ShowcaseThemeCustom.colors.ShowcasePrimary else ShowcaseThemeCustom.colors.ShowcaseDarkGrey,
                    onClick = { button.onClick() }
                )
            }
        }
    }
}

@Composable
private fun FilterButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    onClick: () -> Unit = {}
) {
    Text(
        modifier = modifier
            .defaultMinSize(minWidth = 6.dp)
            .clickable {
                onClick()
            }
            .background(
                color = color,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 6.dp
            ),
        text = text,
        color = ShowcaseThemeCustom.colors.ShowcaseSecondary,
        style = ShowcaseThemeCustom.typography.body,
        fontWeight = FontWeight.W500,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
private fun FilterButtonPreview() {
    ExtendedShowcaseTheme {
        FilterButton(
            text = "Norway",
            color = ShowcaseThemeCustom.colors.ShowcasePrimary
        )
    }
}

@Preview
@Composable
private fun FilterRowPreview() {
    ExtendedShowcaseTheme {
        CountryFilterRow(
            buttons = genMockFilterButtons(),
            buttonSpacing = Dimens.S,
            horizontalContentPadding = Dimens.S
        )
    }
}