package presentation.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentation.component.BulletedList


class SettingScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Conway's Game of Life",
                            style = MaterialTheme.typography.h5
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navigator.pop()
                        }) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, null)
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "Conway's Game of Life is a cellular automaton simulation invented by mathematician John Conway in 1970.",
                    style = MaterialTheme.typography.body1
                )

                Text(
                    text = "Rules:",
                    style = MaterialTheme.typography.h6
                )

                BulletedList(
                    items = listOf(
                        "Any live cell with fewer than two live neighbors dies (underpopulation)",
                        "Any live cell with two or three live neighbors lives on to the next generation",
                        "Any live cell with more than three live neighbors dies (overpopulation)",
                        "Any dead cell with exactly three live neighbors becomes a live cell (reproduction)"
                    )
                )

                Text(
                    text = "The Game of Life demonstrates how complex patterns can emerge from simple rules.",
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }


}