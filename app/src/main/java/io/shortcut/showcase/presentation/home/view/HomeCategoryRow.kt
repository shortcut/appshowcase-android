package io.shortcut.showcase.presentation.home.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.shortcut.showcase.R
import io.shortcut.showcase.data.mapper.GeneralCategory
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import io.shortcut.showcase.util.dimens.Dimens
import io.shortcut.showcase.util.mock.genMockShowcaseAppUI
import io.shortcut.showcase.util.mock.genMockShowcaseAppUIList

@Composable
fun HomeCategoryRow(
    modifier: Modifier = Modifier,
    childModifier: Modifier = Modifier,
    rowTitle: GeneralCategory,
    apps: List<ShowcaseAppUI>,
    onShowAllClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = rowTitle.category,
                style = ShowcaseThemeCustom.typography.h1,
                color = ShowcaseThemeCustom.colors.ShowcaseSecondary
            )
            Spacer(modifier = Modifier.weight(1f))
            // TODO: Disabled show all.
            /*Text(
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onShowAllClick()
                    },
                text = stringResource(id = R.string.home_showAll),
                style = ShowcaseThemeCustom.typography.bodySmall,
                color = ShowcaseThemeCustom.colors.ShowcaseSecondary
            )*/
        }
        LazyRow(
            modifier = childModifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.M),
            contentPadding = PaddingValues(
                start = Dimens.S,
                top = Dimens.S,
                end = Dimens.S
            )
        ) {
            items(apps) { app ->

                CategoryRowItem(
                    imageURL = app.iconUrl,
                    appTitle = app.title,
                    appRating = app.highestRating,
                    onAppIconClick = app.onClick
                )
            }
        }
    }
}

@Composable
private fun CategoryRowItem(
    modifier: Modifier = Modifier,
    imageURL: String,
    appTitle: String,
    appRating: String,
    onAppIconClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .clickable {
                onAppIconClick()
            },
        horizontalAlignment = Alignment.Start
    ) {
        AsyncImage(
            modifier = Modifier
                .size(106.dp)
                .clip(shape = RoundedCornerShape(Dimens.XS)),
            model = imageURL,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(Dimens.XS))
        Text(
            modifier = Modifier
                .sizeIn(maxWidth = 80.dp), // To stop the text from overflowing.
            text = appTitle,
            style = ShowcaseThemeCustom.typography.h2,
            color = ShowcaseThemeCustom.colors.ShowcaseSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (appRating.isNotBlank() || appRating != "0.0") {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = appRating,
                    style = ShowcaseThemeCustom.typography.h3,
                    color = ShowcaseThemeCustom.colors.ShowcaseLightGrey,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(Dimens.XXS))
                Icon(
                    modifier = Modifier
                        .size(Dimens.S),
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = ShowcaseThemeCustom.colors.ShowcaseLightGrey
                )
            }
        } else {
            Text(
                text = stringResource(id = R.string.error_ratingUnknown),
                style = ShowcaseThemeCustom.typography.h3,
                color = ShowcaseThemeCustom.colors.ShowcaseLightGrey,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun CategoryRowItemPreview() {
    val app = genMockShowcaseAppUI()

    ExtendedShowcaseTheme {
        CategoryRowItem(
            imageURL = app.iconUrl,
            appTitle = app.title,
            appRating = app.highestRating
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