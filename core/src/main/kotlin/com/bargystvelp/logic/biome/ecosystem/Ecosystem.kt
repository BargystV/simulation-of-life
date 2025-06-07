package com.bargystvelp.logic.biome.ecosystem

import com.bargystvelp.logic.biome.common.Biome
import com.bargystvelp.logic.cell.common.Cell
import com.bargystvelp.logic.cell.common.Position
import com.bargystvelp.logic.cell.common.Size
import com.bargystvelp.logic.cell.ecosystem.Plant
import com.bargystvelp.logic.cell.ecosystem.Void

data class Ecosystem(override val size: Size) : Biome(size = size) {
    override fun create(): Array<Array<Cell>> {
        return Array(size.width) { x ->
            Array(size.height) { y ->
                val position = Position(x = x, y = y)
                val entity = Plant.trySpawn(position) ?: Void(mutableListOf(position))
                Cell(position = position, entity = entity)
            }
        }
    }
}
