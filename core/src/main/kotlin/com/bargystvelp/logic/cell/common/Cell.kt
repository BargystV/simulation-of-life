package com.bargystvelp.logic.cell.common

import com.bargystvelp.logic.cell.ecosystem.Void

class Cell(
    val position: Position,
    val entity: Entity = Void(position)
)
