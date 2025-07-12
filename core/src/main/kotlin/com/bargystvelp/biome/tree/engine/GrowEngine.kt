package com.bargystvelp.biome.tree.engine

import com.bargystvelp.biome.tree.GENOME_COMPONENT_KEY
import com.bargystvelp.biome.tree.POSITION_COMPONENT_KEY
import com.bargystvelp.biome.tree.component.*
import com.bargystvelp.common.Biome
import com.bargystvelp.common.Color
import com.bargystvelp.common.Component
import com.bargystvelp.common.Engine
import com.bargystvelp.logger.Logger

object GrowEngine : Engine() {
    private val DIRECTIONS = mapOf(
        LEFT to Delta(-1, 0),
        UP to Delta(0, 1),
        RIGHT to Delta(1, 0),
        DOWN to Delta(0, -1),
    )


    override fun tick(biome: Biome, delta: Float) {
        val positionComponent = biome.components[POSITION_COMPONENT_KEY] ?: return
        val genomeComponent = biome.components[GENOME_COMPONENT_KEY] ?: return

        biome.entityFactory.forEachExist { id ->
            val commands = genomeComponent[GenomeComponent.COMMANDS, id]
            if (commands === EMPTY_COMMANDS) return@forEachExist

            val commandNumber = genomeComponent[GenomeComponent.COMMAND_NUMBER, id]
            val directions = commands[commandNumber.toInt()]
            if (directions === EMPTY_DIRECTIONS) return@forEachExist

            val packed = positionComponent[PositionComponent.ID_TO_POS, id]

            val x = PositionComponent.unpackX(packed)
            val y = PositionComponent.unpackY(packed)

            var growUp = false

            directions.forEachIndexed { direction, command ->
                if (command == COMMAND_EMPTY) return@forEachIndexed

                DIRECTIONS[direction]?.let { (dx, dy) ->
                    val newX = wrap(x + dx, biome.biomeSize.width)
                    val newY = clamp(y + dy, biome.biomeSize.height)

                    if (isOccupied(newX, newY, positionComponent)) return@forEachIndexed

                    val child = biome.entityFactory.create()
//                    Logger.info("child: $child")

                    positionComponent[PositionComponent.ID_TO_POS, child] = PositionComponent.pack(newX, newY)

                    genomeComponent[GenomeComponent.COMMAND_NUMBER, child] = command
                    genomeComponent[GenomeComponent.COMMANDS, child] = commands
                    genomeComponent[GenomeComponent.COLOR, child] = Color.WHITE

                    growUp = true
                }
            }

            if (growUp) {
//                Logger.info("growUp")
                genomeComponent[GenomeComponent.COMMAND_NUMBER, id] = COMMAND_EMPTY
                genomeComponent[GenomeComponent.COMMANDS, id] = EMPTY_COMMANDS
                genomeComponent[GenomeComponent.COLOR, id] = Color.PHOTOSYNTHESIS
            }
        }
    }


    private fun isOccupied(x: Int, y: Int, positionComponent: Component): Boolean {
        val packed = PositionComponent.pack(x, y)
        val occupied = positionComponent[PositionComponent.POS_TO_ID, packed] != EMPTY_ID

//        Logger.info("x:$x y:$y isOccupied:${occupied}")
        return occupied
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
