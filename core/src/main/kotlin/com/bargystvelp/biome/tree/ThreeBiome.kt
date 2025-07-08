package com.bargystvelp.biome.tree

import com.bargystvelp.biome.tree.component.ALL_COMMANDS
import com.bargystvelp.biome.tree.component.GenomeComponent
import com.bargystvelp.biome.tree.component.PositionComponent
import com.bargystvelp.biome.tree.engine.GenomeEngine
import com.bargystvelp.biome.tree.entity.TreeEntityFactory
import com.bargystvelp.common.*
import com.bargystvelp.logger.Logger

const val POSITION_COMPONENT_KEY = "POSITION"
const val GENOME_COMPONENT_KEY = "GENOME"

class ThreeBiome(
    windowSize: Size,
    cellSize: Size = Size(width = 10, height = 10),
    biomeSize: Size = windowSize.div(cellSize),
) : Biome(
    windowSize = windowSize,
    cellSize = cellSize,
    biomeSize = biomeSize,
) {
    private val capacity = biomeSize.height * biomeSize.width

    override val renderer: Renderer = TreeRenderer(windowSize, biomeSize, cellSize)
    override val engines: List<Engine> = listOf(GenomeEngine)
    override val entityFactory: EntityFactory = TreeEntityFactory(capacity)
    override val components: Map<String, Component> = mapOf(
        POSITION_COMPONENT_KEY to PositionComponent(capacity),
        GENOME_COMPONENT_KEY to GenomeComponent(capacity),
    )

    init {
        repeat(1) {
            val adam = entityFactory.create()

            Logger.info("adam: $adam")

            components[POSITION_COMPONENT_KEY]?.set(adam, PositionComponent.X, biomeSize.width.div(2))
            components[POSITION_COMPONENT_KEY]?.set(adam, PositionComponent.Y, biomeSize.height.div(2))
            components[GENOME_COMPONENT_KEY]?.set(adam, GenomeComponent.COMMANDS, ALL_COMMANDS)
        }
    }

    override fun tick(delta: Float) {
        engines.forEach { engine ->
            engine.tick(this, delta)
        }
    }
}
