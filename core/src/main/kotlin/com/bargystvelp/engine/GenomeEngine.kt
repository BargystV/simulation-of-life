package com.bargystvelp.engine

import com.bargystvelp.CMD_MOVE_DOWN
import com.bargystvelp.CMD_MOVE_LEFT
import com.bargystvelp.CMD_MOVE_RIGHT
import com.bargystvelp.CMD_MOVE_UP
import com.bargystvelp.CMD_WAIT
import com.bargystvelp.World

object GenomeEngine : Engine() {
    override fun tick(world: World, delta: Float) {
        val width = world.size.width
        val height = world.size.height

        world.entityManager.forEachAlive { id ->
            var x = world.positionManager.getX(id)
            var y = world.positionManager.getY(id)

            for (command in world.genomeManager.getCommands(id)) {
                when (command) {
                    CMD_MOVE_UP    -> y = wrap(y + 1, height)
                    CMD_MOVE_DOWN  -> y = wrap(y - 1, height)
                    CMD_MOVE_LEFT  -> x = wrap(x - 1, width)
                    CMD_MOVE_RIGHT -> x = wrap(x + 1, width)
                    CMD_WAIT       -> {}
                }
            }

            world.positionManager.set(id, x, y)
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
