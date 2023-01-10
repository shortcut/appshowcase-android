package io.shortcut.welcome.components.welcome

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import io.shortcut.welcome.ui.theme.ExtendedInstantTheme
import io.shortcut.welcome.ui.theme.InstantThemeCustom
import io.shortcut.welcometoshortcut.R
import java.util.*

@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var viewState = viewModel.viewState
    val phrase = viewState.currentPhrase

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WelcomeContent(
            word = phrase
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun WelcomeContent(
    modifier: Modifier = Modifier,
    word: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Middle content
        Column(
            modifier = Modifier
                .fillMaxWidth(0.75f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            // Shortcut logo
            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.shortcut_logo_medium_instant),
                contentDescription = "Shortcut Logo",
                colorFilter = ColorFilter.tint(color = Color.Black)
            )

            // Slogan
            Text(
                text = stringResource(id = R.string.shortcut_slogan).uppercase(Locale.ROOT),
                style = InstantThemeCustom.typography.sloganTitle
            )

            // Swappable word
            AnimatedContent(
                targetState = word,
                transitionSpec = {
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                }
            ) { targetWord ->
                Text(
                    text = targetWord.uppercase(Locale.ROOT),
                    style = InstantThemeCustom.typography.sloganTitle,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
private fun WelcomePreview() {
    ExtendedInstantTheme {
        WelcomeContent(
            word = "Hello"
        )
    }
}