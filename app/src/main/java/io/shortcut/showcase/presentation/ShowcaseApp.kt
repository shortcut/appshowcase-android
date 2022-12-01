package io.shortcut.showcase.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.shortcut.showcase.presentation.home.view.HomeScreen
import io.shortcut.showcase.presentation.home.view.HomeViewModel
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme

@Composable
fun ShowcaseApp() {
    ExtendedShowcaseTheme {
        // HomeScreen()
    }
}

@Preview
@Composable
private fun ShowcaseAppPreview() {
    ExtendedShowcaseTheme {
        ShowcaseApp()
    }
}