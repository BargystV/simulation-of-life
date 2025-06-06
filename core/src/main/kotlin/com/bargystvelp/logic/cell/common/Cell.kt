package com.bargystvelp.logic.cell.common

import com.badlogic.gdx.graphics.Color

abstract class Cell(
    open val positions: MutableList<Position>,
    open val color: Color
) {
    abstract fun render(position: Position, cells: Array<Array<Cell>>)
}
