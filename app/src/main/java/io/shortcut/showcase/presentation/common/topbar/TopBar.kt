package io.shortcut.showcase.presentation.common.topbar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.shortcut.showcase.R
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import io.shortcut.showcase.util.dimens.Dimens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    color: Color,
    iconTint: Color,
    onLongClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = color),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .combinedClickable(
                    onLongClick = {
                        onLongClick()
                    }
                ) {},
            painter = painterResource(id = R.drawable.shortcut_logo_small),
            contentDescription = null,
            tint = iconTint
        )
        Spacer(modifier = Modifier.weight(1f))
        // TODO: Currently disabled, needs proper search integration.
        /*Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            tint = iconTint
        )*/
    }
}

@Preview
@Composable
private fun TopBarPreview() {
    ExtendedShowcaseTheme {
        TopBar(
            modifier = Modifier
                .padding(horizontal = Dimens.S),
            color = ShowcaseThemeCustom.colors.ShowcaseBackground,
            iconTint = ShowcaseThemeCustom.colors.ShowcaseSecondary
        )
    }
}