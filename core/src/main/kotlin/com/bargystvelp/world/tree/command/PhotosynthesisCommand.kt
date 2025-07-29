package com.bargystvelp.world.tree.command

import com.bargystvelp.common.World
import com.bargystvelp.world.tree.ENERGY_COMPONENT_KEY
import com.bargystvelp.world.tree.component.EnergyComponent

object PhotosynthesisCommand {
    fun execute(
        world: World,
        id: Int,
        energy: Int
    ) {
        val energyComponent = world.components[ENERGY_COMPONENT_KEY] ?: return

        energyComponent[EnergyComponent.ENERGY, id] = energyComponent[EnergyComponent.ENERGY, id] + energy
    }
}
