package com.bargystvelp.world.tree.command

import com.bargystvelp.common.Color
import com.bargystvelp.common.World
import com.bargystvelp.world.tree.GENOME_COMPONENT_KEY
import com.bargystvelp.world.tree.component.COMMAND_EMPTY
import com.bargystvelp.world.tree.component.EMPTY_COMMANDS
import com.bargystvelp.world.tree.component.GenomeComponent

object GrowUpCommand {
    fun execute(world: World, id: Int) {
        val genomeComponent = world.components[GENOME_COMPONENT_KEY] ?: return

        genomeComponent[GenomeComponent.SEED_COMMAND, id] = COMMAND_EMPTY
        genomeComponent[GenomeComponent.COMMANDS, id] = EMPTY_COMMANDS
        genomeComponent[GenomeComponent.COLOR, id] = Color.PHOTOSYNTHESIS
    }
}
