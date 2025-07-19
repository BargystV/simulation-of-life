package com.bargystvelp.util

import com.bargystvelp.logger.Logger
import java.util.Random

object Randomizer {
    private var random = Random()

    fun init(seed: Long = System.currentTimeMillis()) {
        Logger.info("seed: $seed")

        this.random = Random(seed)
    }

    val get: Random
        get() = random
}
