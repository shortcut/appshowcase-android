package io.shortcut.showcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.shortcut.showcase.presentation.home.view.HomeScreen
import io.shortcut.showcase.presentation.home.view.HomeViewModel
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val homeViewModel: HomeViewModel by viewModels()

        super.onCreate(savedInstanceState)
        setContent {
            ExtendedShowcaseTheme {
                // ShowcaseApp()
                HomeScreen(
                    viewModel = homeViewModel
                )
            }
        }
    }
}