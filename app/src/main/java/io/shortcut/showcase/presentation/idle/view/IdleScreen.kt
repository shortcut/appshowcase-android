package io.shortcut.showcase.presentation.idle.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.remember
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
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.shortcut.showcase.R
import io.shortcut.showcase.presentation.idle.data.CarouselApp
import io.shortcut.showcase.presentation.idle.data.carouselAppList
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import io.shortcut.showcase.util.dimens.Dimens
import io.shortcut.showcase.util.mock.genMockShowcaseAppUI
import java.util.Locale

@Composable
fun IdleScreen(
    onBackClick: () -> Unit = {}
) {
    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = ShowcaseThemeCustom.colors.ShowcaseBackground)
    systemUiController.isNavigationBarVisible = false

    IdleContent(
        apps = carouselAppList,
        onBackClick = onBackClick
    )
}

@Composable
private fun IdleBackground() {
    Column(modifier = Modifier.fillMaxSize()) {
        val vector = ImageVector.vectorResource(id = R.drawable.image)
        val painter = rememberVectorPainter(image = vector)

        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview
@Composable
private fun IdleBackgroundPreview() {
    ExtendedShowcaseTheme {
        IdleBackground()
    }
}

@Composable
private fun IdleContent(
    modifier: Modifier = Modifier,
    apps: List<CarouselApp>,
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = ShowcaseThemeCustom.colors.ShowcaseBackground)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onBackClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Carousel(
            apps = apps
        )
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
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
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
private fun IdleContentPreview() {
    ExtendedShowcaseTheme {
        IdleContent(
            apps = carouselAppList
        )
    }
}