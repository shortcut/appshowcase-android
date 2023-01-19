package io.shortcut.showcase.util.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow

// Helper function for ViewEffects
@Composable
fun <T> ViewEffects(
    viewEffects: SharedFlow<T>,
    block: suspend CoroutineScope.(T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewEffects.collect { block(it) }
    }
}