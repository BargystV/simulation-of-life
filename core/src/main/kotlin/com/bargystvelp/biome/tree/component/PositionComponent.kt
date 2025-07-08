package com.bargystvelp.biome.tree.component

import com.bargystvelp.common.AttrKey
import com.bargystvelp.common.Component

class PositionComponent(maxEntities: Int): Component {
    companion object {                // публичные константы-ключи
        val X = AttrKey<Int>(0)
        val Y = AttrKey<Int>(1)
    }

    private val x = IntArray(maxEntities) { -1 }
    private val y = IntArray(maxEntities) { -1 }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(id: Int, key: AttrKey<T>): T =
        when (key) {
            X -> x[id] as T         // cast единожды внутри компонента
            Y -> y[id] as T
            else -> error("bad key")
        }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> set(id: Int, key: AttrKey<T>, value: T) {
        when (key) {
            X -> x[id] = value as Int
            Y -> y[id] = value as Int
            else -> error("bad key")
        }
    }
}
