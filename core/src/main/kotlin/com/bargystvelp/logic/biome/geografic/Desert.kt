package com.bargystvelp.logic.biome.geografic

import com.bargystvelp.logic.biome.common.Biome
import com.bargystvelp.logic.cell.common.Cell

class Desert(
    override val width: Int,
    override val height: Int
) : Biome(
    width = width,
    height = height
) {
    override fun createBiome(): Array<Array<Cell>> {
        TODO("Not yet implemented")
    }
}
