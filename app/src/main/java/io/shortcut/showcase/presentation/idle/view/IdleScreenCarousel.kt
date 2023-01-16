package io.shortcut.showcase.presentation.idle.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import io.shortcut.showcase.presentation.idle.data.carouselAppList
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun IdleScreenCarousel() {
    val apps = carouselAppList
    val pageCount: Int = apps.size
    val startIndex = 0
    val pagerState = rememberPagerState(
        initialPage = startIndex
    )

    HorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f),
        count = 10,
        state = pagerState,
        verticalAlignment = Alignment.Bottom,
        contentPadding = PaddingValues(horizontal = 90.dp),
        itemSpacing = 20.dp
    ){page->
        Card(modifier = Modifier.graphicsLayer {
            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
            // We animate the scaleX + scaleY, between 85% and 100%
            lerp(
                start = 0.85f,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            ).also { scale ->
                scaleX = scale
                scaleY = scale
            }

            // We animate the alpha, between 50% and 100%
            alpha = lerp(
                start = 0.5f,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            )
        }
            .fillMaxWidth()
            .aspectRatio(1f)
        }){

        }
    }
}

/**
 * Linearly interpolate between [start] and [stop] with [fraction] fraction between them.
 */
fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}

/**
 * Linearly interpolate between [start] and [stop] with [fraction] fraction between them.
 */
fun lerp(start: Int, stop: Int, fraction: Float): Int {
    return start + ((stop - start) * fraction.toDouble()).roundToInt()
}

/**
 * Linearly interpolate between [start] and [stop] with [fraction] fraction between them.
 */
fun lerp(start: Long, stop: Long, fraction: Float): Long {
    return start + ((stop - start) * fraction.toDouble()).roundToLong()
}