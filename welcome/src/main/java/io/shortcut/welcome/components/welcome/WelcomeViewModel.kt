package io.shortcut.welcome.components.welcome

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.shortcut.welcome.util.stringList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeViewModel : ViewModel() {
    var viewState by mutableStateOf(WelcomeViewState())
        private set

    init {
        startWordFlow()
    }

    private fun startWordFlow() {
        viewModelScope.launch {
            val strings = stringList

            strings.forEach { word ->
                viewState = viewState.copy(currentPhrase = word)
                Log.d("ViewState-Words", "Current word set to the viewState: $word")
                delay(1500)
            }
        }
    }
}

data class WelcomeViewState(
    val currentPhrase: String = "Current phrase"
)