package io.shortcut.showcase.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.shortcut.showcase.ui.theme.ShowcaseTheme
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import io.shortcut.showcase.util.dimens.Dimens


@Composable
fun FilterRow(
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        state = lazyListState,
        horizontalArrangement = Arrangement.spacedBy(Dimens.S),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            FilterButton(
                text = "All",
                color = ShowcaseThemeCustom.colors.ShowcaseDarkGrey
            )
        }
        item {
            FilterButton(
                text = "Norway",
                color = ShowcaseThemeCustom.colors.ShowcaseDarkGrey
            )
        }
        item {
            FilterButton(
                text = "Sweden",
                color = ShowcaseThemeCustom.colors.ShowcaseDarkGrey
            )
        }
        item {
            FilterButton(
                text = "Denmark",
                color = ShowcaseThemeCustom.colors.ShowcaseDarkGrey
            )
        }
    }
}

@Composable
private fun FilterButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color
) {
    Text(
        modifier = modifier
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
        fontWeight = FontWeight.W500
    )
}

@Preview
@Composable
private fun FilterButtonPreview() {
    ExtendedShowcaseTheme {
        FilterButton(
            text = "Norway",
            color = ShowcaseThemeCustom.colors.ShowcasePrimary,
        )
    }
}

@Preview
@Composable
private fun FilterRowPreview() {
    ExtendedShowcaseTheme {
        FilterRow(
            modifier = Modifier
                .padding(horizontal = Dimens.M)
        )
    }
}