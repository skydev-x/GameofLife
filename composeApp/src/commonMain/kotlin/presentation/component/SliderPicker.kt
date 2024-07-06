package presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SliderPicker(
    modifier: Modifier = Modifier,
    label: String = "",
    initial: Float = 0f,
    steps: Int = 3,
    range: ClosedFloatingPointRange<Float> = 0f..50f,
    onValueChange: (Float) -> Unit,

    ) {
    var sliderPosition by remember { mutableFloatStateOf(initial) }
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                onValueChange(it)
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.secondary,
                activeTrackColor = MaterialTheme.colors.secondary,
                inactiveTrackColor = MaterialTheme.colors.secondaryVariant,
            ),
            steps = steps,
            valueRange = range
        )
        Text(text = "$label $sliderPosition")
    }
}