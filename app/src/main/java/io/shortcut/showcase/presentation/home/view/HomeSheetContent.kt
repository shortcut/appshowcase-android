package io.shortcut.showcase.presentation.home.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import io.shortcut.showcase.util.dimens.Dimens
import io.shortcut.showcase.util.extensions.isAppInstalled
import io.shortcut.showcase.util.extensions.launchApp
import io.shortcut.showcase.util.extensions.launchPlayStorePage
import io.shortcut.showcase.util.mock.genMockShowcaseAppUI

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    childModifier: Modifier = Modifier,
    appUrl: String,
    appTitle: String,
    appCompany: String,
    appCountry: String,
    appPackageID: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = childModifier
                .size(110.dp)
                .clip(shape = RoundedCornerShape(6.dp)),
            model = appUrl,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(Dimens.L))
        Column(
            modifier = childModifier,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = appTitle,
                style = ExtendedShowcaseTheme.typography.h2,
                color = ExtendedShowcaseTheme.colors.ShowcaseSecondary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = appCompany,
                style = ExtendedShowcaseTheme.typography.body,
                color = ExtendedShowcaseTheme.colors.ShowcaseLightGrey,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = appCountry,
                style = ExtendedShowcaseTheme.typography.bodySmall,
                color = ExtendedShowcaseTheme.colors.ShowcaseLightGrey,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(12.dp))
            val context = LocalContext.current
            val isInstalled = context.isAppInstalled(appPackageID)

            Button(
                onClick = {
                    if (isInstalled) {
                        context.launchApp(appPackageID)
                    } else {
                        context.launchPlayStorePage(appPackageID)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = ExtendedShowcaseTheme.colors.ShowcaseBackground,
                    containerColor = ExtendedShowcaseTheme.colors.ShowcaseSecondary
                ),
                border = BorderStroke(
                    1.dp,
                    color = ExtendedShowcaseTheme.colors.ShowcaseBackground
                ),
                modifier = Modifier.defaultMinSize(minWidth = 125.dp)
            ) {
                Text(text = stringResource(R.string.btn_text_lauch))
            }
        }
    }
}

@Composable
private fun Stats(
    modifier: Modifier = Modifier,
    childModifier: Modifier = Modifier,
    appRating: String,
    appDownloads: String,
    appCategory: String
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
                style = ExtendedShowcaseTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = ExtendedShowcaseTheme.colors.ShowcaseSecondary,
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
                    style = ExtendedShowcaseTheme.typography.h2,
                    color = ExtendedShowcaseTheme.colors.ShowcaseSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(Dimens.XXS))
                Icon(
                    modifier = childModifier
                        .size(Dimens.S),
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = ExtendedShowcaseTheme.colors.ShowcaseSecondary
                )
            }
        }
        Spacer(modifier = Modifier.width(Dimens.M))
        Divider(
            modifier = Modifier
                .height(Dimens.XL)
                .width(1.dp),
            thickness = Dp.Hairline,
            color = ExtendedShowcaseTheme.colors.ShowcaseSecondary
        )
        Spacer(modifier = Modifier.width(Dimens.M))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = childModifier,
                text = stringResource(id = R.string.details_downloadsTitle),
                style = ExtendedShowcaseTheme.typography.bodySmall,
                color = ExtendedShowcaseTheme.colors.ShowcaseSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(Dimens.XXS))
            Text(
                modifier = childModifier,
                text = appDownloads,
                style = ExtendedShowcaseTheme.typography.h2,
                color = ExtendedShowcaseTheme.colors.ShowcaseSecondary,
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
            color = ExtendedShowcaseTheme.colors.ShowcaseSecondary
        )
        Spacer(modifier = Modifier.width(Dimens.M))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = childModifier,
                text = stringResource(id = R.string.details_categoryTitle),
                style = ExtendedShowcaseTheme.typography.bodySmall,
                color = ExtendedShowcaseTheme.colors.ShowcaseSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(Dimens.XXS))
            Text(
                modifier = childModifier,
                text = appCategory,
                style = ExtendedShowcaseTheme.typography.h2,
                color = ExtendedShowcaseTheme.colors.ShowcaseSecondary,
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
    shortDescription: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = childModifier,
            text = stringResource(id = R.string.details_shortDescriptionTitle),
            style = ExtendedShowcaseTheme.typography.h2,
            color = ExtendedShowcaseTheme.colors.ShowcaseSecondary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(Dimens.XS))
        Text(
            modifier = childModifier,
            text = shortDescription,
            style = ExtendedShowcaseTheme.typography.body,
            color = ExtendedShowcaseTheme.colors.ShowcaseSecondary,
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
    onScreenshotClick: (Int, List<String>) -> Unit
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
                        onScreenshotClick(index, screenshots)
                    },
                model = imageURL,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeSheetContent(
    modifier: Modifier = Modifier,
    app: ShowcaseAppUI,
    onScreenshotClick: (Int, List<String>) -> Unit,
    onBackClick: () -> Unit
) {
    val childModifier = Modifier.padding(horizontal = Dimens.L)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = ExtendedShowcaseTheme.colors.ShowcaseBackground)
    ) {
        Column(
            modifier = Modifier.padding(top = 32.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Header(
                modifier = childModifier,
                appUrl = app.iconUrl,
                appTitle = app.title,
                appCompany = app.publisher,
                appCountry = app.country.name,
                appPackageID = app.androidPackageID
            )
            Spacer(modifier = Modifier.height(Dimens.L))
            Stats(
                modifier = childModifier,
                appRating = app.highestRating,
                appDownloads = app.totalInstalls,
                appCategory = app.generalCategory.value
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
            appCountry = app.country.name,
            appPackageID = app.androidPackageID
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
            appCategory = app.generalCategory.value
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
            onScreenshotClick = { _, _ ->
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(backgroundColor = 0xFF161616, showBackground = true)
@Composable
private fun HomeSheetContentPreview() {
    val app = genMockShowcaseAppUI()
    ExtendedShowcaseTheme {
        HomeSheetContent(
            modifier = Modifier,
            app = app,
            onScreenshotClick = { _, _ -> },
            onBackClick = {}
        )
    }
}