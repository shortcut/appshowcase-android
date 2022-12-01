package io.shortcut.showcase.presentation.common.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.shortcut.showcase.R
import io.shortcut.showcase.ui.theme.ExtendedShowcaseTheme
import io.shortcut.showcase.ui.theme.ShowcaseThemeCustom
import io.shortcut.showcase.util.dimens.Dimens

@Composable
fun ShowcaseButton(
    modifier: Modifier = Modifier,
    childModifier: Modifier = Modifier,
    buttonText: String? = null,
    enabled: Boolean,
    onClick: () -> Unit = {}
) {
    val buttonText = buttonText
        ?: if (enabled) {
            stringResource(id = R.string.component_showcaseButtonLaunch)
        } else {
            stringResource(id = R.string.component_showcaseButtonInstall)
        }

    val buttonColor = if (enabled) {
        ShowcaseThemeCustom.colors.ShowcaseSecondary
    } else {
        ShowcaseThemeCustom.colors.ShowcaseBackground
    }

    val textColor = if (enabled) {
        ShowcaseThemeCustom.colors.ShowcaseBackground
    } else {
        ShowcaseThemeCustom.colors.ShowcaseSecondary
    }

    val buttonBorder = if (!enabled) {
        BorderStroke(width = Dp.Hairline, color = ShowcaseThemeCustom.colors.ShowcaseSecondary)
    } else {
        null
    }

    Button(
        modifier = modifier
            .defaultMinSize(
                minWidth = 1.dp,
                minHeight = 1.dp
            ),
        onClick = { onClick() },
        enabled = enabled,
        border = buttonBorder,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            disabledContainerColor = buttonColor
        )
    ) {
        Text(
            modifier = childModifier
                .sizeIn(
                    minHeight = 1.dp,
                    minWidth = 1.dp
                )
                .padding(
                    horizontal = Dimens.fourty,
                    vertical = 10.dp
                ),
            text = buttonText,
            style = ShowcaseThemeCustom.typography.button,
            color = textColor
        )
    }
}

@Preview(backgroundColor = 0xFF161616, showBackground = true)
@Composable
private fun ShowcaseButtonPreview() {
    ExtendedShowcaseTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = ShowcaseThemeCustom.colors.ShowcaseBackground),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            ShowcaseButton(
                enabled = true,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(Dimens.S))
            ShowcaseButton(
                buttonText = "Custom",
                enabled = true,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(Dimens.S))
            ShowcaseButton(
                enabled = false,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(Dimens.S))
            ShowcaseButton(
                buttonText = "Custom",
                enabled = false,
                onClick = {}
            )
        }
    }
}