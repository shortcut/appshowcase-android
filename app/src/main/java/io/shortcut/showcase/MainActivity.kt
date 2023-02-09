package io.shortcut.showcase

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.shortcut.showcase.presentation.home.navigation.MainNavigation
import io.shortcut.showcase.presentation.idle.view.IdleScreen
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Set a timeout state
    private val timeOutState = MutableStateFlow(TimeOutState.ACTIVE)
    private val timer = object : CountDownTimer(TIME_OUT_IN_MILLI, 100) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            emitTimeOut(TimeOutState.TIME_OUT)
        }

        fun reset() {
            this.cancel()
            this.start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        timer.start()
        setContent {
            val timeOut by timeOutState.collectAsState()
            ExtendedShowcaseTheme {
                when (timeOut) {
                    TimeOutState.ACTIVE -> ShowcaseApp()
                    TimeOutState.TIME_OUT -> IdleScreen()
                }
            }
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        if (timeOutState.value == TimeOutState.TIME_OUT) {
            emitTimeOut(state = TimeOutState.ACTIVE)
        }
        timer.reset()
    }

    private fun emitTimeOut(state: TimeOutState) {
        lifecycleScope.launchWhenCreated {
            timeOutState.emit(state)
        }
    }

    companion object {
        const val TIME_OUT_IN_MILLI: Long = 30 * 1000L
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

enum class TimeOutState {
    ACTIVE,
    TIME_OUT
}