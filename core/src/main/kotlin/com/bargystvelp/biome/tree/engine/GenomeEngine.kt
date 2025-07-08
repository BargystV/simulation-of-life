package com.bargystvelp.biome.tree.engine

import com.bargystvelp.biome.tree.GENOME_COMPONENT_KEY
import com.bargystvelp.biome.tree.POSITION_COMPONENT_KEY
import com.bargystvelp.biome.tree.component.CMD_MOVE_DOWN
import com.bargystvelp.biome.tree.component.CMD_MOVE_LEFT
import com.bargystvelp.biome.tree.component.CMD_MOVE_RIGHT
import com.bargystvelp.biome.tree.component.CMD_MOVE_UP
import com.bargystvelp.biome.tree.component.CMD_WAIT
import com.bargystvelp.biome.tree.ThreeBiome
import com.bargystvelp.biome.tree.component.GenomeComponent
import com.bargystvelp.biome.tree.component.PositionComponent
import com.bargystvelp.common.Biome
import com.bargystvelp.common.Engine

object GenomeEngine : Engine() {
    override fun tick(biome: Biome, delta: Float) {
        val width = biome.biomeSize.width
        val height = biome.biomeSize.height

        biome.entityFactory.forEachAlive { id ->
            var x = biome.components[POSITION_COMPONENT_KEY]?.get(id, PositionComponent.X) ?: return@forEachAlive
            var y = biome.components[POSITION_COMPONENT_KEY]?.get(id, PositionComponent.Y) ?: return@forEachAlive

            for (command in biome.components[GENOME_COMPONENT_KEY]?.get(id, GenomeComponent.COMMANDS) ?: return@forEachAlive) {
                when (command) {
                    CMD_MOVE_UP    -> y = wrap(y + 1, height)
                    CMD_MOVE_DOWN  -> y = wrap(y - 1, height)
                    CMD_MOVE_LEFT  -> x = wrap(x - 1, width)
                    CMD_MOVE_RIGHT -> x = wrap(x + 1, width)
                    CMD_WAIT       -> {}
                }
            }

            biome.components[POSITION_COMPONENT_KEY]?.set(id, PositionComponent.X, x)
            biome.components[POSITION_COMPONENT_KEY]?.set(id, PositionComponent.Y, y)
        }
    }


    /**
     * «Обёртка» координаты в диапазоне 0‥size-1 без оператора %.
     *  • Если вышли справа/снизу → вернёт 0.
     *  • Если вышли слева/сверху → вернёт size-1.
     *  • Иначе вернёт исходное значение.
     */
    fun wrap(coordinate: Int, size: Int): Int =
        when {
            coordinate >= size -> coordinate - size   //  size → 0
            coordinate < 0     -> coordinate + size   // -1   → size-1
            else          -> coordinate
        }
}
