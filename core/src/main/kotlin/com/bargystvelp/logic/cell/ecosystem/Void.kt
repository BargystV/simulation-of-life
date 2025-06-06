package com.bargystvelp.logic.cell.ecosystem

import com.bargystvelp.constant.Color.VOID
import com.bargystvelp.logic.cell.common.Entity
import com.bargystvelp.logic.cell.common.Position
import com.bargystvelp.logic.cell.common.Size

class Void(
    override val position: Position,
) : Entity(
    position = position,
    size = Size(width =  1, height = 1),
    color = VOID
)
