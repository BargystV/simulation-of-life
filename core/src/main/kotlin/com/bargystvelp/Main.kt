package com.bargystvelp

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.bargystvelp.biome.tree.GENOME_COMPONENT_KEY
import com.bargystvelp.biome.tree.POSITION_COMPONENT_KEY
import com.bargystvelp.biome.tree.ThreeBiome
import com.bargystvelp.biome.tree.component.GenomeComponent
import com.bargystvelp.biome.tree.component.PositionComponent
import com.bargystvelp.common.Biome
import com.bargystvelp.common.Size
import com.bargystvelp.logger.Logger
import com.bargystvelp.logger.MeasureUtil

class Main : ApplicationAdapter() {
    private lateinit var biome: Biome

    private var renderCount: Int = 0

    override fun create() {
        biome = ThreeBiome(Size(width = Gdx.graphics.width, height = Gdx.graphics.height))
    }

    override fun render() {
        Logger.info("${renderCount++}")

        MeasureUtil.time("Render") {
            biome.render(Gdx.graphics.deltaTime)
        }

        // ► Вычисляем ТОЛЬКО при «одном» нажатии пробела
//        if (!Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) return
        MeasureUtil.time("Tick") {
            biome.tick(Gdx.graphics.deltaTime)
        }
    }


    // Очистка ресурсов
    override fun dispose() {
        Logger.info("")

        biome.renderer.dispose()
    }
}
