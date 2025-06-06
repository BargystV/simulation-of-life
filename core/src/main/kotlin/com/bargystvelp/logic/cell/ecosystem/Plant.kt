package com.bargystvelp.logic.cell.ecosystem

import com.bargystvelp.constant.Color.PLANT
import com.bargystvelp.logic.cell.common.Cell
import com.bargystvelp.logic.cell.common.Position
import kotlin.random.Random

data class Plant(
    override val position: Position,
) : Cell(
    position = position,
    color = PLANT
) {
    companion object {
        private const val SPAWN_CHANCE = 0.00001 // 0.001% шанс

        fun trySpawn(position: Position): Cell {
            return if (Random.nextDouble() < SPAWN_CHANCE) {
                Plant(position)
            } else {
                Void(position)
            }
        }
    }
}
