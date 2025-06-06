package com.bargystvelp.logic.cell.ecosystem

import com.bargystvelp.constant.Color.VOID
import com.bargystvelp.logic.cell.common.Cell
import com.bargystvelp.logic.cell.common.Position

class Void(
    override val positions: MutableList<Position>,// Всегда 1 позиция
) : Cell(
    positions = positions,
    color = VOID
) {
    override fun render(position: Position, cells: Array<Array<Cell>>) {
        cells[position.x][position.y] = Plant.trySpawn(position)
    }
}
