package io.shortcut.showcase.presentation.showAll

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.shortcut.showcase.R
import io.shortcut.showcase.util.dimens.Dimens

@Composable
fun SortAndCategoryFilters(modifier: Modifier, onFilter: () -> Unit, onSort: () -> Unit) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.End) {
        Button(
            onClick = { onFilter() },
            shape = RoundedCornerShape(Dimens.XS),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            modifier = Modifier.defaultMinSize(minHeight = 32.dp)
        ) {
            Text(text = "Finance")
            Spacer(modifier = Modifier.size(Dimens.XS))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_down_triangle_18),
                contentDescription = null,
                tint = Color.Black,
            )
        }
        Spacer(modifier = Modifier.width(Dimens.S))
        IconButton(
            onClick = {
                onSort()
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_sort_oder_24),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
@Preview
fun SortAndCategoryFilterPreview() {
    SortAndCategoryFilters(modifier = Modifier.fillMaxWidth(), onSort = {}, onFilter = {})
}
