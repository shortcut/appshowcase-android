package io.shortcut.showcase.presentation.idle.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.shortcut.showcase.R
import io.shortcut.showcase.presentation.common.gradient.GradientOverlay
import io.shortcut.showcase.presentation.idle.data.CarouselApp
import io.shortcut.showcase.presentation.idle.data.carouselAppList
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import io.shortcut.showcase.util.dimens.Dimens
import io.shortcut.showcase.util.mock.genMockShowcaseAppUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import java.util.Locale

@Composable
fun IdleScreenV2(
    onScreenClick: () -> Unit
) {
    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = ShowcaseThemeCustom.colors.ShowcaseBackground)
    systemUiController.isNavigationBarVisible = false
    IdleContentV2(
        modifier = Modifier.clickable { onScreenClick() },
        apps = carouselAppList
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun IdleContentV2(
    modifier: Modifier = Modifier,
    childModifier: Modifier = Modifier,
    apps: List<CarouselApp>,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = ShowcaseThemeCustom.colors.ShowcaseBackground),
        verticalArrangement = Arrangement.Center
    ) {
        val pageCount: Int = apps.size
        val startIndex = (pageCount / 2)
        val pagerState = rememberPagerState(
            initialPage = startIndex
        )

        Box(
            modifier = childModifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            contentAlignment = Alignment.BottomCenter
        ) {
            val vector = ImageVector.vectorResource(id = R.drawable.image)
            val painter = rememberVectorPainter(image = vector)

            // Doesn't fit design, currently disabled.
            /*Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(color = Color.White)
            )*/

            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                count = 10,
                state = pagerState,
                verticalAlignment = Alignment.Bottom,
                contentPadding = PaddingValues(horizontal = 90.dp),
                itemSpacing = 20.dp
            ) { index ->
                val appIndex = (index - startIndex).floorMod(pageCount)

                CarouselItemV2(
                    iconURL = apps[appIndex].iconURL,
                    title = apps[appIndex].title,
                    category = apps[appIndex].generalCategory.category,
                    shortDescription = apps[appIndex].shortDescription,
                    animationSpeed = 1000,
                    expanded = pagerState.currentPage == index
                )
            }
        }
        Spacer(modifier = Modifier.height(Dimens.L))
        // Doesn't work as expected.
        /*AppDetails(
            appTitle = "Placeholder",
            appCategory = "Placeholder",
            categoryColor = ShowcaseThemeCustom.colors.ShowcaseCategoryOther
        )*/
    }
}

@Composable
private fun AppDetails(
    modifier: Modifier = Modifier,
    childModifier: Modifier = Modifier,
    appTitle: String,
    appCategory: String,
    categoryColor: Color,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = childModifier,
                text = appTitle,
                style = ShowcaseThemeCustom.typography.body,
                color = ShowcaseThemeCustom.colors.ShowcaseSecondary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.width(Dimens.S))
            Text(
                modifier = childModifier
                    .background(
                        color = categoryColor,
                        shape = CircleShape
                    )
                    .padding(
                        horizontal = Dimens.S,
                        vertical = Dimens.XXS
                    ),
                text = appCategory.uppercase(Locale.ROOT),
                style = ShowcaseThemeCustom.typography.body,
                color = ShowcaseThemeCustom.colors.ShowcaseSecondary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.height(Dimens.XS))
        Icon(
            modifier = childModifier,
            painter = painterResource(id = R.drawable.shortcut_logo_small),
            contentDescription = null,
            tint = ShowcaseThemeCustom.colors.ShowcaseSecondary
        )
    }
}

@Preview
@Composable
private fun AppDetailsPreview() {
    ExtendedShowcaseTheme {
        val app = genMockShowcaseAppUI()
        val sampleColor = ShowcaseThemeCustom.colors.ShowcaseCategoryBusiness
        AppDetails(
            appTitle = app.title,
            appCategory = app.generalCategory.category,
            categoryColor = sampleColor
        )
    }
}

@Preview
@Composable
private fun IdleContentV2Preview() {
    val apps = carouselAppList
    ExtendedShowcaseTheme {
        IdleContentV2(
            apps = apps
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
private fun <T> ViewEffects(
    viewEffects: SharedFlow<T>,
    block: suspend CoroutineScope.(T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewEffects.collect { block(it) }
    }
}
