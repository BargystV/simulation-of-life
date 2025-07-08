package com.bargystvelp.component

import com.bargystvelp.constant.Color

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
    private val commands = Array(capacity) {
        ByteArray(COMMAND_SIZE) { CMD_EMPTY }
    }
    private val colors = Array(capacity) {
        Color.PHOTOSYNTHESIS
    }

    fun set(id: Int, commands: ByteArray) {
        this.commands[id] = commands
    }

    fun getCommands(id: Int): ByteArray {
        return commands[id]
    }

    fun getColor(id: Int): com.badlogic.gdx.graphics.Color {
        return colors[id]
    }
}
