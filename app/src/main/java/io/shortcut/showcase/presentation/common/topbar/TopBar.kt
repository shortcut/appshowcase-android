package io.shortcut.showcase.presentation.common.topbar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.shortcut.showcase.R
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme

enum class SearchWidgetState {
    OPENED,
    CLOSED
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopBarWithLogoAndSearch(
    modifier: Modifier = Modifier,
    onLongClick: () -> Unit = {},
    onSearch: (() -> Unit)? = null
) {
    var searchState by remember { mutableStateOf(SearchWidgetState.CLOSED) }
    var searchTextState by remember { mutableStateOf("") }
    when (searchState) {
        SearchWidgetState.OPENED -> {
            SearchWidget(
                modifier = Modifier,
                searchText = searchTextState,
                onSearch = {
                    searchTextState = it
                },
                onCloseSearch = {
                    searchState = SearchWidgetState.CLOSED
                }
            )
        }

        SearchWidgetState.CLOSED -> {
            TopAppBar(
                title = {
                    Icon(
                        modifier = modifier
                            .combinedClickable(
                                onLongClick = {
                                    onLongClick()
                                }, onClick = {}
                            ),
                        painter = painterResource(id = R.drawable.shortcut_logo_small),
                        contentDescription = null,
                        tint = ExtendedShowcaseTheme.colors.ShowcaseSecondary
                    )
                },
                navigationIcon = null,
                actions = {
                    SearchActionIcon(onClick = {
                        onSearch?.invoke()
                        searchState = SearchWidgetState.OPENED
                    })
                },
                backgroundColor = ExtendedShowcaseTheme.colors.ShowcaseBackground,
                contentColor = ExtendedShowcaseTheme.colors.ShowcaseSecondary
            )
        }
    }

}

@Composable
private fun SearchWidget(
    modifier: Modifier,
    searchText: String,
    onSearch: (String) -> Unit,
    onCloseSearch: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .background(color = ExtendedShowcaseTheme.colors.ShowcaseBackground)
            .height(56.dp)
    ) {
        TextField(value = searchText,
            onValueChange = { onSearch(it) },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = stringResource(R.string.search),
                    color = ExtendedShowcaseTheme.colors.ShowcaseSecondary
                )
            },
            singleLine = true,
            maxLines = 1,
            leadingIcon = {
                IconButton(
                    onClick = {
                        onCloseSearch()
                    }
                ) {
                    Icon(
                        modifier = Modifier.alpha(ContentAlpha.high),
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back button",
                        tint = ExtendedShowcaseTheme.colors.ShowcaseSecondary
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onSearch("")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close Icon",
                        tint = ExtendedShowcaseTheme.colors.ShowcaseSecondary
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {

                }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = ExtendedShowcaseTheme.colors.ShowcaseSecondary,
                cursorColor = ExtendedShowcaseTheme.colors.ShowcaseSecondary,
                backgroundColor = ExtendedShowcaseTheme.colors.ShowcaseBackground,
                focusedBorderColor = ExtendedShowcaseTheme.colors.ShowcaseBackground
            ),
            shape = RoundedCornerShape(0.dp)
        )
    }
}

@Composable
fun SearchActionIcon(onClick: () -> Unit) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            tint = ExtendedShowcaseTheme.colors.ShowcaseSecondary
        )
    }
}

@Preview
@Composable
private fun TopBarPreview() {
    ExtendedShowcaseTheme {
        TopBarWithLogoAndSearch(
            modifier = Modifier,
        )
    }
}

@Composable
@Preview
private fun SearchWidgetPreview() {
    ExtendedShowcaseTheme {
        SearchWidget(
            modifier = Modifier,
            searchText = "",
            onSearch = {}, onCloseSearch = {})
    }
}