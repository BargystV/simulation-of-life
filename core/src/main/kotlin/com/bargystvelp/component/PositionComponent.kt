package com.bargystvelp.component

class PositionComponent(maxEntities: Int): Component {
    private val x = IntArray(maxEntities) { -1 }
    private val y = IntArray(maxEntities) { -1 }

    fun set(id: Int, px: Int, py: Int) {
        x[id] = px;
        y[id] = py
    }

    fun getX(id: Int): Int {
        return x[id]
    }

    fun getY(id: Int): Int {
        return y[id]
    }
}
