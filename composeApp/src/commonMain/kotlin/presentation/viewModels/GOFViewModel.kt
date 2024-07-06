package presentation.viewModels

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias CellData = Triple<Int, Int, Boolean>

const val DEFAULT_GRID_SIZE = 25

class GOFViewModel : ScreenModel {


    private val _population = mutableStateListOf<CellData>()
    val population get() = _population

    var rowSize = MutableStateFlow(DEFAULT_GRID_SIZE)
        private set
    private var colSize = MutableStateFlow(DEFAULT_GRID_SIZE)
    private var speed = MutableStateFlow(1000L)

    private var simulationJob: Job? = null

    val lifeState = MutableStateFlow<LifeState>(LifeState.Editing)

    fun edit() {
        lifeState.value = LifeState.Editing
        simulationJob?.cancel()
        simulationJob = null
    }

    fun simulate() {
        lifeState.value = LifeState.Simulating
        if (simulationJob == null) {
            startSimulation()
        }
        simulationJob?.start()
    }

    fun reset() {
        lifeState.value = LifeState.Reset
        simulationJob?.cancel()
        simulationJob = null
        initPopulation()
    }

    fun onSpeedUpdate(new: Float) {
        speed.value = new.toLong()
    }


    init {
        initPopulation()
    }

    private fun initPopulation() {
        _population.clear()
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

    private fun get1DIndex(row: Int, col: Int): Int = row * rowSize.value + col

    fun activeCell(row: Int, col: Int) {
        screenModelScope.launch(Dispatchers.Default) {
            val index = row * rowSize.value + col
            val isActive = _population[index].third
            _population[index] = CellData(row, col, !isActive)
        }
    }


    private val directions = listOf(
        -1 to -1, -1 to 0, -1 to 1,
        0 to -1, 0 to 1,
        1 to -1, 1 to 0, 1 to 1
    )

    private fun countLiveNeighbors(row: Int, col: Int): Int {
        return directions.count { (dr, dc) ->
            val newRow = row + dr
            val newCol = col + dc
            newRow in 0 until rowSize.value && newCol in 0 until colSize.value &&
                    _population[get1DIndex(
                        newRow,
                        newCol
                    )].third
        }
    }


    private fun startSimulation() {
        println("start simulation called")
        simulationJob = screenModelScope.launch(Dispatchers.Default) {
            while (true) {
                nextGeneration()
                delay(speed.value)
            }
        }
        simulationJob?.start()
    }


    private suspend fun nextGeneration() {
        withContext(Dispatchers.Default) {
            // init new population
            val nextPopulation = MutableList<CellData>(rowSize.value * colSize.value) {
                val xy = get2DIndex(it)
                CellData(xy.first, xy.second, false)
            }
            for (cell in _population) {
                val liveNeighbours = countLiveNeighbors(cell.first, cell.second)
                val fate = when {
                    cell.third && liveNeighbours < 2 -> false
                    cell.third && liveNeighbours > 3 -> false
                    !cell.third && liveNeighbours == 3 -> true
                    else -> cell.third
                }
                nextPopulation[get1DIndex(cell.first, cell.second)] =
                    CellData(cell.first, cell.second, fate)
            }
            _population.clear()
            _population.addAll(nextPopulation)
        }
    }


    override fun onDispose() {
        super.onDispose()
        println("VM Disposed")
    }

}

sealed class LifeState {
    data object Reset : LifeState()
    data object Editing : LifeState()
    data object Simulating : LifeState()
}