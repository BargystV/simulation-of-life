package com.bargystvelp.common

interface Component {
    operator fun <T : Any> get(id: Int, key: AttrKey<T>): T
    operator fun <T : Any> set(id: Int, key: AttrKey<T>, value: T)
}

