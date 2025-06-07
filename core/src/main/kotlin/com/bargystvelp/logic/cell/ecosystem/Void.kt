package com.bargystvelp.logic.cell.ecosystem

import com.bargystvelp.constant.Color.VOID
import com.bargystvelp.logic.cell.common.Cell
import com.bargystvelp.logic.cell.common.Entity
import com.bargystvelp.logic.cell.common.Position

class Void(
    override val positions: MutableList<Position>,// Всегда 1 позиция
) : Entity(
    positions = positions,
    color = VOID
) {
    override fun render(position: Position, cells: Array<Array<Cell>>) {
        val entity = Plant.trySpawn(position)
        if (entity == null) return
        cells[position.x][position.y].entity = entity
    }
}
