package com.bargystvelp

import com.bargystvelp.component.ALL_COMMANDS
import com.bargystvelp.component.EntityComponent
import com.bargystvelp.component.GenomeComponent
import com.bargystvelp.component.PositionComponent
import com.bargystvelp.engine.GenomeEngine
import com.bargystvelp.logger.Logger

class World(val size: Size) {
    private val capacity = size.height * size.width

    private val engines = listOf(GenomeEngine)

    val entityComponent = EntityComponent(capacity)
    val positionComponent = PositionComponent(capacity)
    val genomeComponent = GenomeComponent(capacity)


    init {
        repeat(1) {
            val adam = entityComponent.create()

            Logger.info("adam: $adam")

            positionComponent.set(adam, size.width / 2, size.height / 2)
            genomeComponent.set(adam, ALL_COMMANDS)
        }
    }

    fun tick(delta: Float) {
        engines.forEach { engine ->
            engine.tick(this, delta)
        }
    }
}
