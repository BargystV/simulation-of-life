package com.bargystvelp.engine

import com.bargystvelp.component.CMD_MOVE_DOWN
import com.bargystvelp.component.CMD_MOVE_LEFT
import com.bargystvelp.component.CMD_MOVE_RIGHT
import com.bargystvelp.component.CMD_MOVE_UP
import com.bargystvelp.component.CMD_WAIT
import com.bargystvelp.World

object GenomeEngine : Engine() {
    override fun tick(world: World, delta: Float) {
        val width = world.size.width
        val height = world.size.height

        world.entityComponent.forEachAlive { id ->
            var x = world.positionComponent.getX(id)
            var y = world.positionComponent.getY(id)

            for (command in world.genomeComponent.getCommands(id)) {
                when (command) {
                    CMD_MOVE_UP    -> y = wrap(y + 1, height)
                    CMD_MOVE_DOWN  -> y = wrap(y - 1, height)
                    CMD_MOVE_LEFT  -> x = wrap(x - 1, width)
                    CMD_MOVE_RIGHT -> x = wrap(x + 1, width)
                    CMD_WAIT       -> {}
                }
            }

            world.positionComponent.set(id, x, y)
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
