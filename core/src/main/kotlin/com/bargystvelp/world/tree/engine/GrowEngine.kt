package com.bargystvelp.world.tree.engine

import com.bargystvelp.world.tree.GENOME_COMPONENT_KEY
import com.bargystvelp.world.tree.POSITION_COMPONENT_KEY
import com.bargystvelp.common.World
import com.bargystvelp.common.Color
import com.bargystvelp.common.Component
import com.bargystvelp.common.Engine
import com.bargystvelp.world.tree.command.CreateCommand
import com.bargystvelp.world.tree.command.GrowUpCommand
import com.bargystvelp.world.tree.component.COMMAND_EMPTY
import com.bargystvelp.world.tree.component.DOWN
import com.bargystvelp.world.tree.component.EMPTY_COMMANDS
import com.bargystvelp.world.tree.component.EMPTY_DIRECTIONS
import com.bargystvelp.world.tree.component.EMPTY_ID
import com.bargystvelp.world.tree.component.GenomeComponent
import com.bargystvelp.world.tree.component.LEFT
import com.bargystvelp.world.tree.component.PositionComponent
import com.bargystvelp.world.tree.component.RIGHT
import com.bargystvelp.world.tree.component.UP

object GrowEngine : Engine() {
    private val DIRECTIONS = mapOf(
        LEFT to Delta(-1, 0),
        UP to Delta(0, 1),
        RIGHT to Delta(1, 0),
        DOWN to Delta(0, -1),
    )


    override fun tick(world: World, delta: Float) {
        val positionComponent = world.components[POSITION_COMPONENT_KEY] ?: return
        val genomeComponent = world.components[GENOME_COMPONENT_KEY] ?: return

        world.entityFactory.forEachExist { id ->
            val commands = genomeComponent[GenomeComponent.COMMANDS, id]
            if (commands === EMPTY_COMMANDS) return@forEachExist

            val commandNumber = genomeComponent[GenomeComponent.SEED_COMMAND, id]
            val directions = commands[commandNumber.toInt()]
            if (directions === EMPTY_DIRECTIONS) return@forEachExist

            val packed = positionComponent[PositionComponent.ID_TO_POS, id]

            val x = PositionComponent.unpackX(packed)
            val y = PositionComponent.unpackY(packed)

            var growUp = false

            directions.forEachIndexed { direction, command ->
                if (command == COMMAND_EMPTY) return@forEachIndexed

                DIRECTIONS[direction]?.let { (dx, dy) ->
                    val newX = wrap(x + dx, world.biomeSize.width)
                    val newY = clamp(y + dy, world.biomeSize.height)

                    if (isOccupied(newX, newY, positionComponent)) return@forEachIndexed

                    CreateCommand.execute(
                        world = world,
                        packedPosition = PositionComponent.pack(newX, newY),
                        seedCommand = command,
                        commands = commands
                    )

                    growUp = true
                }
            }

            if (growUp) {
                GrowUpCommand.execute(world = world, id)
            }
        }
    }


    private fun isOccupied(x: Int, y: Int, positionComponent: Component): Boolean {
        return positionComponent[PositionComponent.POS_TO_ID, PositionComponent.pack(x, y)] != EMPTY_ID
    }

    /** Обёртка 0‥size-1 (тор). */
    private fun wrap(c: Int, size: Int): Int =
        when {
            c >= size -> c - size
            c < 0 -> c + size
            else -> c
        }

    /** Ограничение 0‥size-1 (зажим). */
    private fun clamp(c: Int, size: Int): Int =
        when {
            c >= size -> size - 1
            c < 0 -> 0
            else -> c
        }

    private data class Delta(val dx: Int, val dy: Int)
}
