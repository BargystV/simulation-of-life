package com.bargystvelp.world.tree.command

import com.badlogic.gdx.graphics.Color
import com.bargystvelp.common.World
import com.bargystvelp.world.tree.GENOME_COMPONENT_KEY
import com.bargystvelp.world.tree.POSITION_COMPONENT_KEY
import com.bargystvelp.world.tree.component.GenomeComponent
import com.bargystvelp.world.tree.component.PositionComponent
import com.bargystvelp.world.tree.component.START_COMMAND

object CreateCommand {
    fun execute(
        world: World,
        packedPosition: Int,
        commands: Array<ByteArray>,
        seedCommand: Byte = START_COMMAND,
        color: Color = com.bargystvelp.common.Color.WHITE
    ) {
        val id = world.entityFactory.create()

        val positionComponent = world.components[POSITION_COMPONENT_KEY] ?: return
        val genomeComponent = world.components[GENOME_COMPONENT_KEY] ?: return

        positionComponent[PositionComponent.ID_TO_POS, id] = packedPosition

        genomeComponent[GenomeComponent.SEED_COMMAND, id] = seedCommand
        genomeComponent[GenomeComponent.COMMANDS, id] = commands
        genomeComponent[GenomeComponent.COLOR, id] = color
    }
}
