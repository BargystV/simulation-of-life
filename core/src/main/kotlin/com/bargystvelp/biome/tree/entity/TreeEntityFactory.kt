package com.bargystvelp.biome.tree.entity

import com.bargystvelp.common.EntityFactory
import com.bargystvelp.logger.Logger


const val FREE_KET = -1
class TreeEntityFactory(private val capacity: Int) : EntityFactory {

    private val ids     = IntArray(capacity) { it }   // [0‥activeCount) живые, дальше свободные
    private val indexOf = IntArray(capacity) { FREE_KET }   // id → idx; -1 = свободен
    private var activeCount = 0                       // кол-во живых (граница сегментов)

    override fun create(): Int {
        require(activeCount < capacity) { "World is full" }

        val id = ids[activeCount]         // первый свободный
        indexOf[id] = activeCount         // помечаем как живой
        activeCount++                     // расширяем активный сегмент
        return id
    }

    override fun destroy(id: Int) {
        val idx = indexOf[id]
        if (idx < 0) return               // уже удалён

        activeCount--
        if (idx != activeCount) {         // swap-remove
            val lastId = ids[activeCount]
            ids[idx] = lastId
            indexOf[lastId] = idx
        }
        indexOf[id] = FREE_KET            // сделал свободным
        ids[activeCount] = id             // кладём в начало free-сегмента (опц.)
    }

    override fun forEachExist(block: (Int) -> Unit) {
        val limit = activeCount           // «снимок» до цикла
        for (i in 0 until limit) block(ids[i])
    }
}

