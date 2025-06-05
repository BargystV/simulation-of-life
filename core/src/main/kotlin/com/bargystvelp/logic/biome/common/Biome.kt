package com.bargystvelp.logic.biome.common

import com.bargystvelp.logic.cell.common.Cell

abstract class Biome(
    open val width: Int,
    open val height: Int,
) {
    val cells: Array<Array<Cell>> by lazy { createBiome() }

    fun render(callback: (Cell) -> Unit) {
        println("render cells: $cells")
        for (x in 0 until width) {
            for (y in 0 until height) {
                callback(cells[x][y])
            }
        }
    }

    abstract fun createBiome(): Array<Array<Cell>>
}
