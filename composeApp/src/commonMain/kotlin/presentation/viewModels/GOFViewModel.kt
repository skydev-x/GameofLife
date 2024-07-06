package presentation.viewModels

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

typealias CellData = Triple<Int, Int, Boolean>

class GOFViewModel : ScreenModel {


    private val _population = mutableStateListOf<CellData>()
    val population get() = _population

    var rowSize = MutableStateFlow(25)
        private set
    var colSize = MutableStateFlow(25)
        private set

    init {
        initPopulation()
    }

    private fun initPopulation() {
        for (i in 0 until rowSize.value * colSize.value) {
            val xy = get2DIndex(i)
            _population.add(CellData(xy.first, xy.second, false))
        }
    }

    private fun get2DIndex(i: Int): Pair<Int, Int> {
        val row = i / rowSize.value
        val col = i % rowSize.value
        return Pair(row, col)
    }


    fun activeCell(row: Int, col: Int) {
        screenModelScope.launch(Dispatchers.Default) {
            val index = row * rowSize.value + col
            val isActive = _population[index].third
            _population[index] = CellData(row, col, !isActive)
            println(_population)
        }
    }


    override fun onDispose() {
        super.onDispose()
        println("VM Disposed")
    }

}