package io.shortcut.showcase.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
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

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    color: Color,
    iconTint: Color,
    onBackArrowClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = color),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (onBackArrowClick != null) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                tint = iconTint
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.shortcut_logo_small),
                contentDescription = null,
                tint = iconTint
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            tint = iconTint
        )
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