package presentation.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentation.component.Cell
import presentation.component.SliderPicker
import presentation.screens.info.InfoScreen
import presentation.viewModels.GOFViewModel
import presentation.viewModels.LifeState

class HomeScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.koinNavigatorScreenModel<GOFViewModel>()
        val population = viewModel.population
        val rowSize by viewModel.rowSize.collectAsState()
        val lifeState by viewModel.lifeState.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Compose Game of Life",
                    style = MaterialTheme.typography.h5
                )
                IconButton(onClick = {
                    navigator.push(InfoScreen())
                }) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.padding(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    viewModel.simulate()

                }) {
                    Text("Start")
                }


                Button(onClick = {
                    viewModel.edit()

                }) {
                    Text("Edit")
                }


                Button(onClick = {
                    viewModel.reset()
                }) {
                    Text("Reset")
                }
            }


            Spacer(modifier = Modifier.padding(16.dp))



            LazyVerticalGrid(
                columns = GridCells.Fixed(rowSize),
                modifier = Modifier.size(rowSize.times(16.dp)).border(1.dp, Color.Gray)
            ) {
                items(population, key = { "${it.first}-${it.second}-${it.third}" }) {
                    Cell(
                        isActive = it.third,
                        row = it.first,
                        column = it.second
                    ) { row, col ->
                        if (lifeState !is LifeState.Simulating) {
                            viewModel.activeCell(row, col)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(16.dp))

            SliderPicker(
                label = "Speed",
                initial = 500f,
                range = 200f..5000f,
                steps = 10
            ) {
                viewModel.onSpeedUpdate(it)
            }

        }
    }
}