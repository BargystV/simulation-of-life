package com.bargystvelp.world.tree.engine

import com.bargystvelp.common.Engine
import com.bargystvelp.common.World
import com.bargystvelp.world.tree.GENOME_COMPONENT_KEY
import com.bargystvelp.world.tree.POSITION_COMPONENT_KEY
import com.bargystvelp.world.tree.command.EnergySpendCommand
import com.bargystvelp.world.tree.component.COMMAND_EMPTY
import com.bargystvelp.world.tree.component.GenomeComponent
import com.bargystvelp.world.tree.component.PositionComponent

object RespirationEngine : Engine() {

    private const val WOOD_COST = 3

    override fun tick(world: World, delta: Float) {
        val positionComponent = world.components[POSITION_COMPONENT_KEY] ?: return
        val genomeComponent   = world.components[GENOME_COMPONENT_KEY]   ?: return

        world.entityFactory.forEachExist { id ->
            var energyCost = 0

            val cells = positionComponent[PositionComponent.ID_TO_POS_LIST, id]
            cells.forEach { packedPos ->
                // семечко не тратит энергию
                if (genomeComponent[GenomeComponent.SEED_COMMAND_AT_POS, packedPos] == COMMAND_EMPTY) {
                    energyCost += WOOD_COST
                }
            }

            if (energyCost != 0) {
                EnergySpendCommand.execute(world, id, energyCost)
            }
        }
    }
}

