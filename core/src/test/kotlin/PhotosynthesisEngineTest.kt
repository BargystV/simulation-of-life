@file:Suppress("RemoveRedundantQualifierName")

import com.bargystvelp.common.Size
import com.bargystvelp.common.World
import com.bargystvelp.util.PositionUtils
import com.bargystvelp.world.tree.ENERGY_COMPONENT_KEY
import com.bargystvelp.world.tree.GENOME_COMPONENT_KEY
import com.bargystvelp.world.tree.POSITION_COMPONENT_KEY
import com.bargystvelp.world.tree.TreeWorld
import com.bargystvelp.world.tree.component.*
import com.bargystvelp.world.tree.engine.PhotosynthesisEngine
import com.bargystvelp.world.tree.entity.TreeEntityFactory
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals

/**
 * Полный, изолированный и читаемый набор тестов для `PhotosynthesisEngine`.
 *
 * Каждый сценарий выводит доску «до» и «после» – по логу сразу видно,
 * какие клетки дали энергию и на сколько, а также как повлияла тень.
 */
@DisplayName("PhotosynthesisEngine – exhaustive spec")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PhotosynthesisEngineTest {

    /* ─────────── размеры доски ─────────── */

    private val W   = 4              // ширина
    private val H   = 4              // высота
    private val CAP = 8              // макс. число организмов

    /* ─────────── инфраструктура мира ─────────── */

    private lateinit var world           : World
    private lateinit var pos             : PositionComponent
    private lateinit var genome          : GenomeComponent
    private lateinit var energy          : EnergyComponent
    private lateinit var factory         : TreeEntityFactory

    @BeforeEach
    fun setUp() {
        /* World и все его компоненты – реальные из проекта */
        world   = TreeWorld(Size(W, H))

        pos = world.components[POSITION_COMPONENT_KEY] as PositionComponent
        genome = world.components[GENOME_COMPONENT_KEY] as GenomeComponent
        energy = world.components[ENERGY_COMPONENT_KEY] as EnergyComponent
        factory = world.entityFactory as TreeEntityFactory

        /* 🔧 Сброс стартовой энергии, которую задаёт TreeWorld  */
        for (id in 0 until CAP) {
            energy[EnergyComponent.ENERGY, id] = 0
        }
    }

    /* ────────────────── тесты ────────────────── */

    @Test
    @DisplayName("Без тени – лист на самом нижнем ряду даёт 3 энергии")
    fun noShade_bottomLeafGives3() {
        val id = factory.create()                   // создаём организм
        plantLeaf(id, 1, 0)                         // (x=1, y=0)

        logSnapshot("before")
        PhotosynthesisEngine.tick(world, 1f)
        logSnapshot("after")

        assertEquals(3, energy[EnergyComponent.ENERGY, id])
    }

    @Test
    @DisplayName("Высокий лист (y=2) без тени даёт 9 энергии")
    fun noShade_highLeafGives9() {
        val id = factory.create()
        plantLeaf(id, 0, 2)             // (x=0, y=2)

        logSnapshot("before")            // ← добавили
        PhotosynthesisEngine.tick(world, 1f)
        logSnapshot("after")             // ← добавили

        assertEquals(9, energy[EnergyComponent.ENERGY, id]) // (y+1=3) * 3 = 9
    }


    @Test
    @DisplayName("Тень собственного дерева уменьшает коэффициент")
    fun shadeFromOwnTree() {
        val id = factory.create()
        /* три листа строго друг над другом */
        plantLeaf(id, 1, 2)   // y=2 вверх
        plantLeaf(id, 1, 1)   // y=1
        plantLeaf(id, 1, 0)   // y=0 (будет в тени двух верхних)

        logSnapshot("before")          // ← добавлено
        PhotosynthesisEngine.tick(world, 1f)
        logSnapshot("after")           // ← добавлено

        // energyGain:
        //  y=2 : (3 * 3) = 9   (нет тени)
        //  y=1 : (2 * 2) = 4   (одна клетка покрывает)
        //  y=0 : (1 * 1) = 1   (две клетки покрывают)
        assertEquals(14, energy[EnergyComponent.ENERGY, id])
    }


    @Test
    @DisplayName("Семечко даёт тень, но само не копит энергию")
    fun seedShadesButGivesNoEnergy() {
        val plant    = factory.create()
        val neighbor = factory.create()

        plantLeaf(plant,    0, 0)   // лист на земле
        plantSeed(neighbor, 0, 1)   // семечко прямо над ним

        logSnapshot("before")       // ← добавлено
        PhotosynthesisEngine.tick(world, 1f)
        logSnapshot("after")        // ← добавлено

        // Лист в тени одного уровня: (y+1 = 1) * (3‑1) = 2
        assertEquals(2, energy[EnergyComponent.ENERGY, plant])
        // Семечко энергии не даёт
        assertEquals(0, energy[EnergyComponent.ENERGY, neighbor])
    }


    /* ─────────── вспомогательные утилиты ─────────── */

    /** Сажает листовую клетку (фотосинтезирующую) */
    private fun plantLeaf(id: Int, x: Int, y: Int) {
        val packed = PositionUtils.pack(x, y)
        pos[PositionComponent.POS_TO_ID, packed] = id
        genome[GenomeComponent.SEED_COMMAND_AT_POS, packed] = COMMAND_EMPTY
    }

    /** Сажает семечко – тень есть, энергии нет */
    private fun plantSeed(id: Int, x: Int, y: Int) {
        val packed = PositionUtils.pack(x, y)
        pos[PositionComponent.POS_TO_ID, packed] = id
        genome[GenomeComponent.SEED_COMMAND_AT_POS, packed] = START_COMMAND
    }

    /** Печать доски и энергий – удобно смотреть в логе */
    private fun logSnapshot(stage: String) {
        println("── $stage ──")
        for (yy in (H - 1) downTo 0) {
            for (xx in 0 until W) {
                val packed  = PositionUtils.pack(xx, yy)
                val cellId  = pos[PositionComponent.POS_TO_ID, packed]
                if (cellId == EMPTY_ID) {
                    print(". ")
                } else {
                    val seed = genome[GenomeComponent.SEED_COMMAND_AT_POS, packed] != COMMAND_EMPTY
                    if (seed) print("s ") else print("$cellId ")
                }
            }
            println()
        }
        println("energy: " + (0 until CAP)
            .filter { energy[EnergyComponent.ENERGY, it] != 0 }
            .joinToString { "id=$it→${energy[EnergyComponent.ENERGY, it]}" })
        println()
    }
}
