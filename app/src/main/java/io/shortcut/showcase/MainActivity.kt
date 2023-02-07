package io.shortcut.showcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.shortcut.showcase.presentation.home.navigation.MainNavigation
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExtendedShowcaseTheme {
                ShowcaseApp()
            }
        }
    }
}

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