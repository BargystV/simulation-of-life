package com.bargystvelp.logic.cell.ecosystem

import com.bargystvelp.constant.Color.PLANT
import com.bargystvelp.logic.cell.common.Cell
import com.bargystvelp.logic.cell.common.Position
import kotlin.random.Random

data class Plant(
    override val positions: MutableList<Position>,
) : Cell(
    positions = positions,
    color = PLANT
) {
    override fun render(position: Position, cells: Array<Array<Cell>>) {

    }

    companion object {
        private const val SPAWN_CHANCE = 0.00001 // 0.001% шанс

        fun trySpawn(position: Position): Cell {
            return if (Random.nextDouble() < SPAWN_CHANCE) {
                Plant(mutableListOf(position))
            } else {
                Void(mutableListOf(position))
            }
        }
    }
}
