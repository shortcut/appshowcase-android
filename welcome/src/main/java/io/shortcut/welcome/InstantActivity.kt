package io.shortcut.welcome

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import io.shortcut.welcome.components.welcome.WelcomeScreen
import io.shortcut.welcome.ui.theme.ExtendedInstantTheme

class InstantActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExtendedInstantTheme {
                WelcomeScreen()
            }
        }
    }
}