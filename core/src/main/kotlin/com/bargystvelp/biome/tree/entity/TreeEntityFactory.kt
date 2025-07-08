package com.bargystvelp.biome.tree.entity

import com.bargystvelp.common.EntityFactory

class TreeEntityFactory(private val capacity: Int): EntityFactory {
    private val freeIds  = IntArray(capacity) {
        capacity - 1 - it // оптимизация сдвига
    }
    private var freeTop = capacity
    private val alive = BooleanArray(capacity)

    override fun create(): Int {
        check(freeTop > 0) { "World is full" }

        val id = freeIds[--freeTop]

        alive[id] = true

        return id
    }

    override fun destroy(id: Int) {
        if (!alive[id]) return

        alive[id] = false
        freeIds[freeTop++] = id
    }

    override fun forEachAlive(block: (Int) -> Unit) {
        for (id in 0 until capacity) if (alive[id]) block(id)
    }
}
