package com.bargystvelp.logic.cell.ecosystem

import com.bargystvelp.constant.Color.VOID
import com.bargystvelp.logic.cell.common.Cell
import com.bargystvelp.logic.cell.common.Position

class Void(
    override val position: Position,
) : Cell(
    position = position,
    color = VOID
)
