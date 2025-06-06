package com.bargystvelp.logic.cell.ecosystem

import com.bargystvelp.constant.Color.PLANT
import com.bargystvelp.logic.cell.common.Entity
import com.bargystvelp.logic.cell.common.Position
import com.bargystvelp.logic.cell.common.Size
import kotlin.random.Random

data class Plant(
    override val position: Position,
) : Entity(
    position = position,
    size = Size(width =  8, height = 8),
    color = PLANT
) {
    companion object {
        private const val SPAWN_CHANCE = 0.00001 // 0.001% шанс

        fun trySpawn(position: Position): Plant? {
            return if (Random.nextDouble() < SPAWN_CHANCE) {
                Plant(position)
            } else {
                null
            }
        }
    }
}
