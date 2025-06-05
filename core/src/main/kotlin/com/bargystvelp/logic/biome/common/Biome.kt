package com.bargystvelp.logic.biome.common

import com.bargystvelp.logic.cell.common.Cell

abstract class Biome(
    open val width: Int,
    open val height: Int,
) {
    val cells: Array<Array<Cell>>

    init {
        cells = createBiome(width, height)
    }

    fun render(callback: (Cell) -> Unit) {
        for (x in 0 until width) {
            for (y in 0 until height) {
                callback(cells[x][y])
            }
        }
    }

    protected abstract fun createBiome(width: Int, height: Int): Array<Array<Cell>>
}
