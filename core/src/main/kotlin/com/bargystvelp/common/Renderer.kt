package com.bargystvelp.common

import com.badlogic.gdx.graphics.Color

interface Renderer {
    /** Подготовить кадр (очистка буфера, Pixmap, и т.д.) */
    fun begin()

    /** Нарисовать одного энтити (внутри себя сам использует Pixmap или что нужно) */
    fun draw(x: Int, y: Int, color: Color)

    /** Завершить кадр (перенести Pixmap в Texture, отрисовать через SpriteBatch и т.д.) */
    fun end()

    fun dispose()
}
