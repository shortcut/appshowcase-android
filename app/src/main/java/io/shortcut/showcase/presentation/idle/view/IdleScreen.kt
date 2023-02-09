package io.shortcut.showcase.presentation.idle.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.shortcut.showcase.R
import io.shortcut.showcase.presentation.idle.data.CarouselApp
import io.shortcut.showcase.presentation.idle.data.carouselAppList
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.util.dimens.Dimens
import io.shortcut.showcase.util.mock.genMockShowcaseAppUI
import java.util.Locale

@Composable
fun IdleScreen() {
    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = ExtendedShowcaseTheme.colors.ShowcaseBackground)
    systemUiController.isNavigationBarVisible = false
    IdleContent(
        apps = carouselAppList,
    )
}

@Composable
private fun IdleBackground(modifier: Modifier) {
    val vector = ImageVector.vectorResource(id = R.drawable.image)
    val painter = rememberVectorPainter(image = vector)
    Image(
        modifier = modifier
            .fillMaxWidth(),
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.FillWidth
    )
}

@Preview
@Composable
private fun IdleBackgroundPreview() {
    ExtendedShowcaseTheme {
        IdleBackground(modifier = Modifier)
    }
}

@Composable
private fun IdleContent(
    modifier: Modifier = Modifier,
    apps: List<CarouselApp>
) {
    Box(modifier = Modifier.fillMaxSize()) {
        var index by remember { mutableStateOf(0) }
        IdleBackground(modifier = Modifier.align(Alignment.TopCenter))
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Carousel(
                apps = apps
            ) {
                index = it
            }
            val shownApp = apps[index]
            AppDetails(
                appTitle = shownApp.title,
                appCategory = shownApp.generalCategory.value,
                categoryColor = Color.Blue
            )
        }
    }
}

@Composable
private fun AppDetails(
    modifier: Modifier = Modifier,
    childModifier: Modifier = Modifier,
    appTitle: String,
    appCategory: String,
    categoryColor: Color
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = Dimens.M),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = childModifier,
                text = appTitle,
                style = ExtendedShowcaseTheme.typography.body,
                color = ExtendedShowcaseTheme.colors.ShowcaseSecondary,
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
                style = ExtendedShowcaseTheme.typography.body,
                color = ExtendedShowcaseTheme.colors.ShowcaseSecondary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.height(Dimens.XS))
        Icon(
            modifier = childModifier,
            painter = painterResource(id = R.drawable.shortcut_logo_small),
            contentDescription = null,
            tint = ExtendedShowcaseTheme.colors.ShowcaseSecondary
        )
    }
}

@Preview
@Composable
private fun AppDetailsPreview() {
    ExtendedShowcaseTheme {
        val app = genMockShowcaseAppUI()
        val sampleColor = ExtendedShowcaseTheme.colors.ShowcaseCategoryBusiness
        AppDetails(
            appTitle = app.title,
            appCategory = app.generalCategory.value,
            categoryColor = sampleColor
        )
    }
}

@Preview
@Composable
private fun IdleContentPreview() {
    ExtendedShowcaseTheme {
        IdleContent(
            apps = carouselAppList
        )
    }
}