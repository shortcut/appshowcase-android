package io.shortcut.showcase.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.shortcut.showcase.util.dimens.Dimens

@Composable
fun VerticalRadioButtonGroup(radioOptions: List<String>, onOption: (String) -> Unit = {}) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1]) }
    Column(modifier = Modifier.fillMaxWidth()) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = text,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = Dimens.XS),
                    color = Color.White
                )
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) }
                )
            }
        }
    }
}

@Composable
@Preview
private fun VerticalRadioButtonGroupPreview() {
    VerticalRadioButtonGroup(radioOptions = listOf("A", "B", "C"))
}