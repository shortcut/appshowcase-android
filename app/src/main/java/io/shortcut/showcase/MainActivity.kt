package io.shortcut.showcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import io.shortcut.showcase.presentation.ShowcaseApp
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