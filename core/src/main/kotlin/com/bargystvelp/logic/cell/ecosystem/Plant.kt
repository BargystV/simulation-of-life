package com.bargystvelp.logic.cell.ecosystem

import com.bargystvelp.constant.Color.PLANT
import com.bargystvelp.logic.cell.common.Cell
import com.bargystvelp.logic.cell.common.Entity
import com.bargystvelp.logic.cell.common.Position
import kotlin.random.Random

data class Plant(
    override val positions: MutableList<Position>,
) : Entity(
    positions = positions,
    color = PLANT
) {
    companion object {
        const val SPAWN_CHANCE = 0.00001 // 0.001% шанс

        fun trySpawn(position: Position): Entity? {
            return if (Random.nextDouble() < SPAWN_CHANCE) {
                Plant(mutableListOf(position))
            } else {
                null
            }
        }
    }

    override fun render(position: Position, cells: Array<Array<Cell>>) {

    }
}
