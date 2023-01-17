package io.shortcut.showcase.presentation.idle.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import io.shortcut.showcase.presentation.idle.data.CarouselApp
import io.shortcut.showcase.presentation.idle.data.carouselAppList
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import io.shortcut.showcase.util.dimens.Dimens
import io.shortcut.showcase.util.mock.genMockShowcaseAppUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import java.util.Locale

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Carousel(
    modifier: Modifier = Modifier,
    apps: List<CarouselApp>
) {
    val pageCount: Int = apps.size
    val startIndex = pageCount / 2

    val pagerState = rememberPagerState(
        initialPage = startIndex
    )

    // Auto scrolls the carousel
    var autoscroll by remember { mutableStateOf(false) }
    LaunchedEffect(autoscroll) {
        while (true) {
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
            delay(5000)
        }
    }

    HorizontalPager(
        modifier = modifier
            .fillMaxSize(),
        count = Int.MAX_VALUE,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 90.dp),
        itemSpacing = 20.dp,
        verticalAlignment = Alignment.Bottom
    ) { index ->
        val appIndex = (index - startIndex).floorMod(pageCount)

        CarouselItemBeta(
            iconURL = apps[appIndex].iconURL,
            title = apps[appIndex].title,
            category = apps[appIndex].generalCategory.category,
            shortDescription = apps[appIndex].shortDescription,
            animationSpeed = 500,
            expanded = pagerState.currentPage == index
        )
    }
}

@Composable
private fun CarouselItem(
    modifier: Modifier = Modifier,
    childModifier: Modifier = Modifier,
    appIconURL: String,
    appTitle: String,
    appCategory: String,
    shortDescription: String,
    expanded: Boolean
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(374.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = ShowcaseThemeCustom.colors.ShowcaseSecondary
        ),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            AsyncImage(
                modifier = childModifier
                    .size(50.dp)
                    .align(Alignment.End),
                model = appIconURL,
                contentDescription = null
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = childModifier,
                text = appTitle,
                style = ShowcaseThemeCustom.typography.h1,
                color = ShowcaseThemeCustom.colors.ShowcaseBackground,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(Dimens.XXS))
            Text(
                modifier = childModifier,
                text = appCategory.uppercase(Locale.ROOT),
                style = ShowcaseThemeCustom.typography.bodySmall,
                color = ShowcaseThemeCustom.colors.ShowcaseGrey,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(Dimens.XXS))
            Text(
                modifier = childModifier,
                text = shortDescription,
                style = ShowcaseThemeCustom.typography.body,
                color = ShowcaseThemeCustom.colors.ShowcaseBackground,
                overflow = TextOverflow.Ellipsis,
                maxLines = 4,
                fontWeight = FontWeight.W400
            )
        }
    }
}

@Composable
private fun IconCollapsed(
    modifier: Modifier = Modifier,
    appIconURL: String
) {
    AsyncImage(
        modifier = modifier
            .size(150.dp)
            .clip(shape = RoundedCornerShape(10.dp)),
        model = appIconURL,
        contentDescription = null
    )
}

@Preview
@Composable
private fun CarouselItemPreview() {
    ExtendedShowcaseTheme {
        val app = genMockShowcaseAppUI()
        CarouselItem(
            modifier = Modifier
                .padding(
                    start = Dimens.M,
                    top = Dimens.M,
                    end = Dimens.M,
                    bottom = Dimens.L

                ),
            appIconURL = app.iconUrl,
            appTitle = app.title,
            appCategory = app.generalCategory.category,
            shortDescription = app.shortDescription,
            expanded = true
        )
    }
}

@Preview
@Composable
private fun IconCollapsedPreview() {
    ExtendedShowcaseTheme {
        val app = genMockShowcaseAppUI()
        IconCollapsed(
            appIconURL = app.iconUrl
        )
    }
}

@Preview
@Composable
private fun CarouselPreview() {
    ExtendedShowcaseTheme {
        Carousel(
            apps = carouselAppList
        )
    }
}

// Helper function to create an infinite loop for the pager
private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}

// Helper function for ViewEffects
@Composable
fun <T> ViewEffects(
    viewEffects: SharedFlow<T>,
    block: suspend CoroutineScope.(T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewEffects.collect { block(it) }
    }
}