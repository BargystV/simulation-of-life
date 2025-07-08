package com.bargystvelp.biome.tree

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.bargystvelp.CameraHandler
import com.bargystvelp.common.Renderer
import com.bargystvelp.common.Size
import com.bargystvelp.common.times

class TreeRenderer(
    windowSize: Size,
    biomeSize: Size,
    val cellSize: Size,
): Renderer {

    private var spriteBatch = SpriteBatch()
    private var shapeRenderer = ShapeRenderer()

    private lateinit var pixmap: Pixmap
    private lateinit var gridTexture: Texture

    init {
        CameraHandler.init(windowSize)
        initGrid(biomeSize, cellSize)
    }

    override fun begin() {
        // Обработка ввода и обновление камеры
        CameraHandler.instance.handle()

        shapeRenderer.projectionMatrix = CameraHandler.instance.combined
        spriteBatch.projectionMatrix = CameraHandler.instance.combined

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
    }

    override fun draw(x: Int, y: Int, color: Color) {
        shapeRenderer.color = color
        shapeRenderer.rect(
            (x.times(cellSize.width)).toFloat(),
            (y.times(cellSize.height)).toFloat(),
            cellSize.width.toFloat(),
            cellSize.height.toFloat()
        )
    }

    override fun end() {
        shapeRenderer.end()

        // drawGrid
        spriteBatch.begin()
        spriteBatch.draw(gridTexture, 0f, 0f)
        spriteBatch.end()
    }

    override fun dispose() {
        pixmap.dispose()
        gridTexture.dispose()
        spriteBatch.dispose()

        shapeRenderer.dispose()
    }


    private fun initGrid(biomeSize: Size, cellSize: Size) {
        val windowSize = biomeSize.times(cellSize) // Правильно умножать, а не брать исходный windowSize

        pixmap = Pixmap(windowSize.width, windowSize.height, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.DARK_GRAY)

        // Вертикальные линии
        for (x in 0..biomeSize.width) {
            val px = x.times(cellSize.width)
            pixmap.drawLine(px, 0, px, windowSize.height)
        }

        // Горизонтальные линии
        for (y in 0..biomeSize.height) {
            val py = y.times(cellSize.height)
            pixmap.drawLine(0, py, windowSize.width, py)
        }

        gridTexture = Texture(pixmap)
    }
}
