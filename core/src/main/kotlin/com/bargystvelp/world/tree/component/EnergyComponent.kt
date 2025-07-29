package com.bargystvelp.world.tree.component

import com.bargystvelp.common.AttrKey
import com.bargystvelp.common.Component

const val DEFAULT_ENERGY = 300
const val ENERGY_TO_GROW = 18

class EnergyComponent(
    private val maxEntities: Int
): Component {

    companion object {
        val ENERGY = AttrKey<Int, Int>(0)

        fun hasEnoughEnergy(energy: Int): Boolean = energy >= ENERGY_TO_GROW
    }

    private val energies = IntArray(maxEntities) { DEFAULT_ENERGY }

    /* ───────────── Component API ───────────── */
    @Suppress("UNCHECKED_CAST")
    override fun <K, V : Any> set(type: AttrKey<K, V>, key: K, value: V) {
        when (type) {
            ENERGY  -> energies[key as Int] = value as Int
            else    -> error("bad AttrKey for EnergyComponent")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <K, V : Any> get(type: AttrKey<K, V>, key: K): V =
        when (type) {
            ENERGY  -> energies[key as Int] as V
            else    -> error("bad AttrKey for GenomeComponent")
        }
}
