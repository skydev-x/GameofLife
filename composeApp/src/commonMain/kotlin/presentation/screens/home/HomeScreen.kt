package presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentation.screens.settings.SettingScreen
import presentation.viewModels.GOFViewModel

class HomeScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.koinNavigatorScreenModel<GOFViewModel>()

        val number by viewModel.number.collectAsState()
        Column {

            Text("Home $number")
            Button(onClick = {
                navigator.push(SettingScreen())
            }) {
                Text("To settings")
            }
        }
    }
}