package com.bargystvelp.logic.cell.ecosystem

import com.badlogic.gdx.graphics.Color
import com.bargystvelp.constant.Color.PLANT
import com.bargystvelp.logic.cell.common.Cell
import com.bargystvelp.logic.cell.common.Height

data class Plant(
    override val x: Int,
    override val y: Int,
    override val height: Height,
) : Cell(
    x = x,
    y = y,
    height = height
) {
    override fun getColor(): Color {
        return PLANT
    }}
