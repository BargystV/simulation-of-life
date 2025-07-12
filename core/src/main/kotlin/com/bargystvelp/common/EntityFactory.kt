package com.bargystvelp.common

interface EntityFactory {
    /** Новый ID или ошибка, если мир заполнен. */
    fun create(): Int

    /** Освободить ID. */
    fun destroy(id: Int)

    /** Быстрый проход по живым сущностям. */
    fun forEachExist(block: (Int) -> Unit)
}
