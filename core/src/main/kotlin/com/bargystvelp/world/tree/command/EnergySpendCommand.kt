package com.bargystvelp.world.tree.command

import com.bargystvelp.common.World
import com.bargystvelp.logger.Logger
import com.bargystvelp.world.tree.ENERGY_COMPONENT_KEY
import com.bargystvelp.world.tree.component.EnergyComponent

object EnergySpendCommand {
    fun execute(
        world: World,
        id: Int,
        energyCost: Int
    ) {
        val energyComponent = world.components[ENERGY_COMPONENT_KEY] ?: return

        energyComponent[EnergyComponent.ENERGY, id] = energyComponent[EnergyComponent.ENERGY, id] - energyCost
    }
}
