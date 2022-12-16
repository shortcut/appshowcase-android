package io.shortcut.showcase.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.shortcut.showcase.R
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import io.shortcut.showcase.util.dimens.Dimens
import io.shortcut.showcase.util.mock.genMockShowcaseAppUI


@Composable
private fun Header(
    modifier: Modifier = Modifier,
    childModifier: Modifier = Modifier,
    appUrl: String,
    appTitle: String,
    appCompany: String,
    appCountry: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = childModifier
                .size(80.dp)
                .clip(shape = RoundedCornerShape(6.dp)),
            model = appUrl,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(Dimens.S))
        Column(
            modifier = childModifier,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = appTitle,
                style = ShowcaseThemeCustom.typography.h2,
                color = ShowcaseThemeCustom.colors.ShowcaseSecondary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = appCompany,
                style = ShowcaseThemeCustom.typography.body,
                color = ShowcaseThemeCustom.colors.ShowcaseLightGrey,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = appCountry,
                style = ShowcaseThemeCustom.typography.bodySmall,
                color = ShowcaseThemeCustom.colors.ShowcaseLightGrey,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}


@Composable
private fun Stats(
    modifier: Modifier = Modifier,
    childModifier: Modifier = Modifier,
    appRating: String,
    appDownloads: String,
    appCategory: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = childModifier,
                text = stringResource(id = R.string.details_ratingTitle),
                style = ShowcaseThemeCustom.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = ShowcaseThemeCustom.colors.ShowcaseSecondary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Spacer(modifier = Modifier.height(Dimens.XXS))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = childModifier,
                    text = appRating,
                    style = ShowcaseThemeCustom.typography.h2,
                    color = ShowcaseThemeCustom.colors.ShowcaseSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(Dimens.XXS))
                Icon(
                    modifier = childModifier
                        .size(Dimens.S),
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = ShowcaseThemeCustom.colors.ShowcaseSecondary
                )
            }
        }
        Spacer(modifier = Modifier.width(Dimens.M))
        Divider(
            modifier = Modifier
                .height(Dimens.XL)
                .width(1.dp),
            thickness = Dp.Hairline,
            color = ShowcaseThemeCustom.colors.ShowcaseSecondary
        )
        Spacer(modifier = Modifier.width(Dimens.M))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = childModifier,
                text = stringResource(id = R.string.details_downloadsTitle),
                style = ShowcaseThemeCustom.typography.bodySmall,
                color = ShowcaseThemeCustom.colors.ShowcaseSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(Dimens.XXS))
            Text(
                modifier = childModifier,
                text = appDownloads,
                style = ShowcaseThemeCustom.typography.h2,
                color = ShowcaseThemeCustom.colors.ShowcaseSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(Dimens.M))
        Divider(
            modifier = Modifier
                .height(Dimens.XL)
                .width(1.dp),
            thickness = Dp.Hairline,
            color = ShowcaseThemeCustom.colors.ShowcaseSecondary
        )
        Spacer(modifier = Modifier.width(Dimens.M))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = childModifier,
                text = stringResource(id = R.string.details_categoryTitle),
                style = ShowcaseThemeCustom.typography.bodySmall,
                color = ShowcaseThemeCustom.colors.ShowcaseSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(Dimens.XXS))
            Text(
                modifier = childModifier,
                text = appCategory,
                style = ShowcaseThemeCustom.typography.h2,
                color = ShowcaseThemeCustom.colors.ShowcaseSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun ShortDescription(
    modifier: Modifier = Modifier,
    childModifier: Modifier = Modifier,
    shortDescription: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = childModifier,
            text = stringResource(id = R.string.details_shortDescriptionTitle),
            style = ShowcaseThemeCustom.typography.h2,
            color = ShowcaseThemeCustom.colors.ShowcaseSecondary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(Dimens.XS))
        Text(
            modifier = childModifier,
            text = shortDescription,
            style = ShowcaseThemeCustom.typography.body,
            color = ShowcaseThemeCustom.colors.ShowcaseSecondary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 4
        )
    }
}

@Composable
private fun Screenshots(
    screenshots: List<String>,
    horizontalContentPadding: Dp = Dimens.M,
    itemSpacing: Dp = Dimens.S,
    onScreenshotClick: (Int) -> Unit,
) {
    // TODO: Fix padding and modifiers.
    val lazyListState = rememberLazyListState()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(itemSpacing),
        verticalAlignment = Alignment.CenterVertically,
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = horizontalContentPadding),
    ) {
        itemsIndexed(screenshots) { index, imageURL ->
            AsyncImage(
                modifier = Modifier
                    .width(92.dp)
                    .height(160.dp)
                    .clip(shape = RoundedCornerShape(6.dp))
                    .clickable {
                        onScreenshotClick(index)
                    },
                model = imageURL,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )
        }
    }
}

@Composable
fun HomeSheetContent(
    modifier: Modifier = Modifier,
    childModifier: Modifier = Modifier,
    app: ShowcaseAppUI,
    onScreenshotClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = ShowcaseThemeCustom.colors.ShowcaseBackground),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(Dimens.M))
        Header(
            modifier = childModifier,
            appUrl = app.iconUrl,
            appTitle = app.title,
            appCompany = app.publisher,
            appCountry = app.country.name
        )
        Spacer(modifier = Modifier.height(Dimens.L))
        Stats(
            modifier = childModifier,
            appRating = app.highestRating,
            appDownloads = app.totalInstalls,
            appCategory = app.generalCategory.category
        )
        Spacer(modifier = Modifier.height(Dimens.L))
        ShortDescription(
            modifier = childModifier,
            shortDescription = app.shortDescription
        )
        Spacer(modifier = Modifier.height(Dimens.S))
        Screenshots(
            screenshots = app.screenshots.imageURLs,
            onScreenshotClick = onScreenshotClick
        )
        Spacer(modifier = Modifier.height(Dimens.XL))
    }
}

@Preview(backgroundColor = 0xFF161616, showBackground = true)
@Composable
private fun HeaderPreview() {
    val app = genMockShowcaseAppUI()
    ExtendedShowcaseTheme {
        Header(
            appUrl = app.iconUrl,
            appTitle = app.title,
            appCompany = app.publisher,
            appCountry = app.country.name
        )
    }
}

@Preview(backgroundColor = 0xFF161616, showBackground = true)
@Composable
private fun StatsPreview() {
    val app = genMockShowcaseAppUI()
    ExtendedShowcaseTheme {
        Stats(
            appRating = app.highestRating,
            appDownloads = app.totalInstalls,
            appCategory = app.generalCategory.category
        )
    }
}

@Preview(backgroundColor = 0xFF161616, showBackground = true)
@Composable
private fun ShortDescriptionPreview() {
    val app = genMockShowcaseAppUI()
    ExtendedShowcaseTheme {
        ShortDescription(
            shortDescription = app.shortDescription
        )
    }
}

@Preview(backgroundColor = 0xFF161616, showBackground = true)
@Composable
private fun ScreenshotsPreview() {
    val app = genMockShowcaseAppUI()
    ExtendedShowcaseTheme {
        Screenshots(
            screenshots = app.screenshots.imageURLs,
            horizontalContentPadding = Dimens.S,
            itemSpacing = Dimens.XS,
            onScreenshotClick = {}
        )
    }
}

@Preview(backgroundColor = 0xFF161616, showBackground = true)
@Composable
private fun HomeSheetContentPreview() {
    val app = genMockShowcaseAppUI()
    ExtendedShowcaseTheme {
        HomeSheetContent(
            childModifier = Modifier
                .padding(horizontal = Dimens.M),
            app = app,
            onScreenshotClick = {}
        )
    }
}