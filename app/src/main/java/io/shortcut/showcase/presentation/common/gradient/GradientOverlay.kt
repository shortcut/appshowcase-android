package io.shortcut.showcase.presentation.common.gradient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme

@Composable
fun GradientOverlay(
    modifier: Modifier = Modifier,
    bottomColor: Color,
    topColor: Color
) {
    val colors = listOf(bottomColor, topColor)
    val brush = Brush.verticalGradient(colors)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush)
    )
}

@Preview
@Composable
private fun GradientOverlayPreview() {
    ExtendedShowcaseTheme {
        val topColor = Color(0xFF161616)
        val bottomColor = Color.Transparent

        GradientOverlay(
            topColor = topColor,
            bottomColor = bottomColor
        )
    }
}

@Preview
@Composable
private fun GradientOverlayOverColumnPreview() {
    ExtendedShowcaseTheme {
        val topColor = Color(0xFF161616)
        val bottomColor = Color.Transparent

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Content is here",
                textAlign = TextAlign.Center,
                style = ExtendedShowcaseTheme.typography.h3,
                color = Color.Black
            )
            GradientOverlay(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f)
                    .align(Alignment.BottomCenter),
                topColor = topColor,
                bottomColor = bottomColor
            )
        }
    }
}