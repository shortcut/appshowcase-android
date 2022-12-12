package io.shortcut.showcase.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import io.shortcut.showcase.presentation.home.navigation.MainNavigation
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme

@Composable
fun ShowcaseApp() {
    ExtendedShowcaseTheme {
        val navController = rememberNavController()
        MainNavigation(navController = navController)
    }
}

@Preview
@Composable
private fun ShowcaseAppPreview() {
    ExtendedShowcaseTheme {
        ShowcaseApp()
    }
}