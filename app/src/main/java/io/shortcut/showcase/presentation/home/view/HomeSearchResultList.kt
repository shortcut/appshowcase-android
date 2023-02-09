package io.shortcut.showcase.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.util.dimens.Dimens
import io.shortcut.showcase.util.mock.genMockShowcaseAppUI

@Composable
fun HomeSearchResultList(
    paddingValues: PaddingValues,
    homeAppsState: HomeAppSearchState,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ExtendedShowcaseTheme.colors.ShowcaseBackground)
            .padding(paddingValues),
        contentPadding = PaddingValues(Dimens.S)
    ) {
        items(homeAppsState.searchResults) {
            HomeSearchAppItem(it)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.M)
            )
        }
    }
}

@Composable
fun HomeSearchAppItem(app: ShowcaseAppUI) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            app.onClick()
        }, verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(shape = RoundedCornerShape(Dimens.XS)),
            model = app.iconUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.width(Dimens.S))
        Text(
            modifier = Modifier.weight(1f), // To stop the text from overflowing.
            text = app.title,
            style = ExtendedShowcaseTheme.typography.h2,
            color = ExtendedShowcaseTheme.colors.ShowcaseSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
@Preview
private fun HomeSearchAppItemPreview() {
    ExtendedShowcaseTheme {
        HomeSearchAppItem(app = genMockShowcaseAppUI())
    }
}



