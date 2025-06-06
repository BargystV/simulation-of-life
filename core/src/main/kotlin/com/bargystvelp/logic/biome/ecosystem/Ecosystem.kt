package com.bargystvelp.logic.biome.ecosystem

import com.bargystvelp.logic.biome.common.Biome
import com.bargystvelp.logic.cell.common.Cell
import com.bargystvelp.logic.cell.common.Position
import com.bargystvelp.logic.cell.common.Size
import com.bargystvelp.logic.cell.ecosystem.Plant

data class Ecosystem(override val size: Size) : Biome(size = size) {
    override fun create(): Array<Array<Cell>> {
        return Array(size.width) { x ->
            Array(size.height) { y ->
                val position = Position(x = x, y = y)
                val plant = Plant.trySpawn(position = position)

                if (plant != null) {
                    Cell(position, plant)
                } else {
                    Cell(position)
                }
            }
        }
    }
}
