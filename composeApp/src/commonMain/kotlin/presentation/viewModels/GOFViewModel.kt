package presentation.viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GOFViewModel : ScreenModel {

    val number = MutableStateFlow(0)

    init {
        update()
    }

    private fun update() {
        screenModelScope.launch(Dispatchers.Default) {
            repeat(100) {
                number.value += 1
                delay(1000)
            }
        }
    }

    override fun onDispose() {
        super.onDispose()
        println("VM Disposed")
    }

}