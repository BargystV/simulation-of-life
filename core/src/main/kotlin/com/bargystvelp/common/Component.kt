package com.bargystvelp.common

interface Component {
    operator fun <K, V : Any> set(type: AttrKey<K, V>, key: K, value: V)
    operator fun <K, V : Any> get(type: AttrKey<K, V>, key: K): V
}

