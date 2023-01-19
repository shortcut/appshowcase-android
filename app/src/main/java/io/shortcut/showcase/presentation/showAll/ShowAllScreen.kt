package io.shortcut.showcase.presentation.showAll

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import io.shortcut.showcase.presentation.common.filter.view.FilterRow
import io.shortcut.showcase.presentation.home.view.CategoryRowItem

@Composable
fun ShowAllScreen(
    onBackClick: () -> Boolean,
    showAllViewModel: ShowAllViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {
        val state by showAllViewModel.showAppListSState.collectAsState()
        FilterRow(buttons = state.countryFilter)
        val lazyListState = rememberLazyListState()
        LazyColumn(state = lazyListState) {
            items(state.apps) { app ->
                CategoryRowItem(
                    imageURL = app.iconUrl,
                    appTitle = app.title,
                    appRating = app.highestRating
                )
            }
        }
    }
}