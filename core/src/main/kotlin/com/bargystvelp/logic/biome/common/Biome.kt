package com.bargystvelp.logic.biome.common

import com.bargystvelp.logic.cell.common.Cell
import com.bargystvelp.logic.cell.common.Position
import com.bargystvelp.logic.cell.common.Size

abstract class Biome(
    open val size: Size
) {
    val cells: Array<Array<Cell>> by lazy { create() }

    fun render(): Array<Array<Cell>> {
        for (x in 0 until size.width) {
            for (y in 0 until size.height) {
                cells[x][y].render(position = Position(x, y), cells)
            }
        }

        return cells;
    }



    protected abstract fun create(): Array<Array<Cell>>
}

