package io.shortcut.showcase.presentation.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.util.dimens.Dimens

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun ScreenshotScreen(
    onBackArrowClick: () -> Unit,
    images: List<String>,
    startIndex: Int
) {
    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = ExtendedShowcaseTheme.colors.ShowcaseBackground)
    systemUiController.isNavigationBarVisible = false

    val pageCount: Int = images.size
    val pagerState = rememberPagerState(
        initialPage = startIndex
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = ExtendedShowcaseTheme.colors.ShowcaseBackground,
        topBar = {
            TopAppBar(
                title = {},
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackArrowClick()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null,
                            tint = ExtendedShowcaseTheme.colors.ShowcaseSecondary
                        )
                    }
                },
                actions = {},
                backgroundColor = ExtendedShowcaseTheme.colors.ShowcaseBackground,
                contentColor = ExtendedShowcaseTheme.colors.ShowcaseSecondary,
                elevation = 0.dp
            )
        }
    ) { padding ->
        if (images.isNotEmpty()) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimens.S),
                state = pagerState,
                count = pageCount,
                itemSpacing = Dimens.S,
                verticalAlignment = Alignment.CenterVertically,
                contentPadding = padding
            ) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize(),
                        model = images[index],
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ScreenshotScreenPreview() {
    ExtendedShowcaseTheme {
        ScreenshotScreen(
            onBackArrowClick = {},
            startIndex = 0,
            images = emptyList()
        )
    }
}