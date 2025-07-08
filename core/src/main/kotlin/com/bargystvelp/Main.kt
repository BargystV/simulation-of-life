package com.bargystvelp

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.bargystvelp.logger.Logger

class Main : ApplicationAdapter() {

    companion object {
        val CELL_SIZE = Size(10, 10)
    }

    private lateinit var pixmap: Pixmap
    private lateinit var gridTexture: Texture
    private lateinit var spriteBatch: SpriteBatch
    private lateinit var shapeRenderer: ShapeRenderer

    private lateinit var world: World

    private lateinit var windowSize: Size

    private var renderCount: Int = 0

    override fun create() {
        windowSize = Size(width = Gdx.graphics.width, height = Gdx.graphics.height)
        val biomeSize = windowSize.div(CELL_SIZE)

        Logger.info("windowSize: $windowSize")
        Logger.info("biomeSize: $biomeSize")

        world = World(size = biomeSize)

        // Создание рендерера для отрисовки клеток
        shapeRenderer = ShapeRenderer()
        spriteBatch = SpriteBatch()

        // Инициализация камеры с размерами мира
        CameraHandler.init(width = windowSize.width.toFloat(), height = windowSize.height.toFloat())

        initGrid(biomeSize, CELL_SIZE)
    }

    override fun render() {
        renderCount++

        // Обработка ввода и обновление камеры
        CameraHandler.instance.handle()
        shapeRenderer.projectionMatrix = CameraHandler.instance.combined

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        world.tick(delta = Gdx.graphics.deltaTime)

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        var entityCounter = 0
        world.entityComponent.forEachAlive { id ->
            entityCounter++
            shapeRenderer.color = world.genomeComponent.getColor(id)

            val x = world.positionComponent.getX(id)
            val y = world.positionComponent.getY(id)

            shapeRenderer.rect(
                (x * CELL_SIZE.width).toFloat(),
                (y * CELL_SIZE.height).toFloat(),
                CELL_SIZE.width.toFloat(),
                CELL_SIZE.height.toFloat()
            )
        }

        shapeRenderer.end()

        spriteBatch.projectionMatrix = CameraHandler.instance.combined
        spriteBatch.begin()
        spriteBatch.draw(gridTexture, 0f, 0f)
        spriteBatch.end()
    }


    // Очистка ресурсов
    override fun dispose() {
        pixmap.dispose()
        gridTexture.dispose()
        spriteBatch.dispose()

        shapeRenderer.dispose()

        Logger.info("")
    }


    private fun initGrid(biomeSize: Size, cellSize: Size) {
        val width = biomeSize.width * cellSize.width
        val height = biomeSize.height * cellSize.height

        pixmap = Pixmap(width, height, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.DARK_GRAY)

        // Вертикальные линии
        for (x in 0..biomeSize.width) {
            val px = x * cellSize.width
            pixmap.drawLine(px, 0, px, height)
        }

        // Горизонтальные линии
        for (y in 0..biomeSize.height) {
            val py = y * cellSize.height
            pixmap.drawLine(0, py, width, py)
        }

        gridTexture = Texture(pixmap)
    }

}
