package com.bargystvelp.biome.tree.component

import com.badlogic.gdx.graphics.Color
import com.bargystvelp.common.AttrKey
import com.bargystvelp.common.Component

const val COMMAND_SIZE = 64

// ==== COMMANDS ====
const val CMD_EMPTY: Byte = -1
const val CMD_MOVE_UP: Byte = 0
const val CMD_MOVE_DOWN: Byte = 1
const val CMD_MOVE_LEFT: Byte = 2
const val CMD_MOVE_RIGHT: Byte = 3
const val CMD_WAIT: Byte = 4

val ALL_COMMANDS = byteArrayOf(CMD_MOVE_UP, CMD_MOVE_DOWN, CMD_MOVE_LEFT, CMD_MOVE_RIGHT, CMD_WAIT)

class GenomeComponent(capacity: Int): Component {
    companion object {
        val COMMANDS = AttrKey<ByteArray>(0)
        val COLOR    = AttrKey<Color>(1)
    }

    private val commands = Array(capacity) {
        ByteArray(COMMAND_SIZE) { CMD_EMPTY }
    }
    private val colors = Array(capacity) {
        com.bargystvelp.common.Color.PHOTOSYNTHESIS
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(id: Int, key: AttrKey<T>): T =
        when (key) {
            COMMANDS -> commands[id]    as T
            COLOR    -> colors[id]      as T
            else     -> error("bad key")
        }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> set(id: Int, key: AttrKey<T>, value: T) {
        when (key) {
            COMMANDS -> commands[id] = value as ByteArray
            COLOR    -> colors[id] = value as Color
            else     -> error("bad key")
        }
    }


}
