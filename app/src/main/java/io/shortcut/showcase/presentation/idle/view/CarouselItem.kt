package io.shortcut.showcase.presentation.idle.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import io.shortcut.showcase.util.dimens.Dimens
import io.shortcut.showcase.util.mock.genMockShowcaseAppUI
import java.util.Locale

@Composable
fun CarouselItem(
    modifier: Modifier = Modifier,
    iconURL: String,
    title: String,
    category: String,
    shortDescription: String,
    animationSpeed: Int,
    expanded: Boolean
) {
    // var expanded by remember { mutableStateOf(true) } // .clickable { expanded = !expanded }

    /** General transition **/
    val transition = updateTransition(
        targetState = expanded,
        label = "Card expanding"
    )

    /** Card stuff **/
    val cardWidth by transition.animateDp(
        transitionSpec = { tween(durationMillis = animationSpeed) },
        label = "Card width expansion"
    ) { isExpanded ->
        if (isExpanded) 200.dp else 150.dp
    }

    val cardHeight by transition.animateDp(
        transitionSpec = { tween(durationMillis = animationSpeed) },
        label = "Card height expansion"
    ) { isExpanded ->
        if (isExpanded) 373.dp else 150.dp
    }

    val cardCornerRadius by transition.animateDp(
        transitionSpec = { tween(durationMillis = animationSpeed) },
        label = "Card corner radius"
    ) { isExpanded ->
        if (isExpanded) 10.dp else 0.dp
    }

    val cardBackgroundColor by transition.animateColor(
        label = "Card background color"
    ) { isExpanded ->
        if (isExpanded) ShowcaseThemeCustom.colors.ShowcaseSecondary else Color.Transparent
    }

    val cardGeneralPadding by transition.animateDp(
        transitionSpec = { tween(durationMillis = animationSpeed) },
        label = "Card general padding"
    ) { isExpanded ->
        if (isExpanded) 24.dp else 0.dp
    }

    val cardBottomPadding by transition.animateDp(
        transitionSpec = { tween(durationMillis = animationSpeed) },
        label = "Card bottom padding"
    ) { isExpanded ->
        if (isExpanded) 48.dp else 0.dp
    }

    /** Icon stuff **/
    val iconSize by transition.animateDp(
        transitionSpec = { tween(durationMillis = animationSpeed) },
        label = "Icon size expansion"
    ) { isExpanded ->
        if (isExpanded) 50.dp else 150.dp
    }

    val iconCornerRadius by transition.animateDp(
        transitionSpec = { tween(durationMillis = animationSpeed) },
        label = "Icon corner radius"
    ) { isExpanded ->
        if (isExpanded) 0.dp else 10.dp
    }

    /** Text stuff **/

    /** End of all digusting variables. **/
    Card(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight),
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor
        ),
        shape = RoundedCornerShape(cardCornerRadius)
    ) {
        Column(
            modifier = Modifier
                .width(cardWidth)
                .height(cardHeight)
                .padding(
                    start = cardGeneralPadding,
                    top = cardGeneralPadding,
                    end = cardGeneralPadding,
                    bottom = cardBottomPadding
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            /** Icon **/
            AsyncImage(
                model = iconURL,
                contentDescription = null,
                modifier = Modifier
                    .size(iconSize)
                    .clip(shape = RoundedCornerShape(iconCornerRadius))
                    .align(Alignment.End),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.weight(1f))
            /** Text views **/
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column {
                    Text(
                        modifier = Modifier,
                        text = title,
                        style = ShowcaseThemeCustom.typography.h1,
                        color = ShowcaseThemeCustom.colors.ShowcaseBackground,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(Dimens.XXS))
                    Text(
                        modifier = Modifier,
                        text = category.uppercase(Locale.ROOT),
                        style = ShowcaseThemeCustom.typography.bodySmall,
                        color = ShowcaseThemeCustom.colors.ShowcaseGrey,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(Dimens.XXS))
                    Text(
                        modifier = Modifier,
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
    }
}

@Preview
@Composable
private fun CarouselItemV2Preview() {
    val mockApp = genMockShowcaseAppUI()
    val speed = 1000
    ExtendedShowcaseTheme {
        CarouselItem(
            iconURL = mockApp.iconUrl,
            title = mockApp.title,
            category = mockApp.generalCategory.category,
            shortDescription = mockApp.shortDescription,
            animationSpeed = speed,
            expanded = true
        )
    }
}