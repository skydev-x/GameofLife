package presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Cell(
    modifier: Modifier = Modifier,
    row: Int,
    column: Int,
    isActive: Boolean,
    onClick: (Int, Int) -> Unit
) {
    Box(
        modifier =
        modifier
            .size(10.dp)
            .background(if (isActive) Color.White else Color.Black)
            .border(1.dp, Color.Gray)
            .clickable {
                onClick(row, column)
            }
    )
}