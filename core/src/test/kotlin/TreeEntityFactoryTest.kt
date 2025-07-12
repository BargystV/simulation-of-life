@file:Suppress("RemoveRedundantQualifierName", "SpellCheckingInspection")

package com.bargystvelp.biome.tree.entity

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

/**
 *  ⚡  TreeEntityFactory-test
 *
 *  Покрывает ВСЕ публичные сценарии работы:
 *  1.  Сохранение порядка рождения;
 *  2.  Отложенная активация «новорожденных»;
 *  3.  Удаление головы / хвоста / середины и сохранение порядка;
 *  4.  Удаление прямо во время обхода;
 *  5.  Переиспользование освобождённых id-шников;
 *  6.  Переполнение пула.
 *
 *  Логи печатаются так, чтобы _одним взглядом_ увидеть,
 *  кто жив, кто родился, кого удалили и в каком порядке шёл обход.
 *
 *  Пример:
 *  ── tick#2 (iterate) ──
 *  → 0,1,2
 *      create 3
 *      destroy 1
 *  итог: [0,2]
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TreeEntityFactoryTest {

    /* ----------- настройки ----------- */

    private val CAP   = 10                       // ёмкость фабрики
    private lateinit var f: TreeEntityFactory

    /* ----------- утилиты логирования ----------- */

    private fun log(header: String, bodies: List<String> = emptyList()) {
        println("\n── $header ──")
        bodies.forEach { println(it) }
    }

    private fun snapshot(): List<Int> {
        val list = mutableListOf<Int>()
        f.forEachExist { list += it }
        return list
    }

    private fun snapshotLog(tag: String) =
        "$tag: ${snapshot().joinToString(prefix = "[", postfix = "]")}"

    /* ============================================ */

    @BeforeEach fun setup() { f = TreeEntityFactory(CAP) }

    /* ---------- 1. порядок рождения ---------- */

    @Test fun `creation order is preserved`() {
        val created = (0..4).map { f.create() }                        // 0,1,2,3,4
        log("after create", listOf(snapshotLog("curr")))
        assertEquals(created, snapshot(), "порядок рождения сломан")
    }

    /* ---------- 2. новорожденные отложены ---------- */

    @Test fun `newborn appear only next tick and впереди очереди`() {
        repeat(3) { f.create() }                                       // 0,1,2

        // первый тик
        val visited1 = mutableListOf<Int>()
        f.forEachExist { id ->
            visited1 += id
            if (id == 1) f.create()                                   // рождаем 3
        }
        log("tick#1 (iterate)",
            listOf("→ " + visited1.joinToString(),
                snapshotLog("после тика")))
        assertEquals(listOf(0,1,2), visited1, "новорожденный попал в текущий тик")

        // второй тик
        val visited2 = mutableListOf<Int>()
        f.forEachExist { visited2 += it }
        log("tick#2 (iterate)",
            listOf("→ " + visited2.joinToString(),
                snapshotLog("после тика")))
        assertEquals(listOf(3,0,1,2), visited2,
            "новорожденный должен быть ПЕРЕД старожилами в следующем тике")
    }

    /* ---------- 3. удаление головы, хвоста, середины ---------- */

    @Test fun `destroy keeps relative order`() {
        val ids = (0..5).map { f.create() }                            // 0..5
        f.destroy(ids[0])                                              // remove head (0)
        f.destroy(ids[3])                                              // remove middle (3)
        f.destroy(ids[5])                                              // remove tail (5)

        val order = snapshot()
        log("after destroy head,mid,tail", listOf(snapshotLog("curr")))
        assertEquals(listOf(1,2,4), order, "оставшиеся ids в неверном порядке")
    }

    /* ---------- 4. удаление прямо во время обхода ---------- */

    @Test fun `destroy during iteration does not break snapshot`() {
        repeat(4) { f.create() }                                       // 0..3

        val visited = mutableListOf<Int>()
        f.forEachExist { id ->
            visited += id
            if (id == 1) f.destroy(1)
            if (id == 2) f.destroy(2)
        }
        log("tick (destroy in-loop)",
            listOf("→ " + visited.joinToString(),
                snapshotLog("после тика")))
        assertEquals(listOf(0,1,2,3), visited, "снимок должен быть фиксирован")
        assertEquals(listOf(0,3), snapshot(), "после тика остались неверные id")
    }

    /* ---------- 5. переиспользование освобождённого id ---------- */

    @Test fun `reused id treated as newborn and идёт впереди`() {
        val first = f.create()                                         // 0
        val second = f.create()                                        // 1
        f.forEachExist { /* прогрев */ }

        f.destroy(first)                                               // освободили 0
        val new0 = f.create()                                          // вернулся 0

        val order = mutableListOf<Int>()
        f.forEachExist { order += it }
        log("reused id", listOf("→ " + order.joinToString()))
        assertEquals(listOf(new0, second), order, "`reused` должен идти первым")
    }

    /* ---------- 6. переполнение пула ---------- */

    @Test fun `factory throws when capacity exceeded`() {
        repeat(CAP) { f.create() }
        val ex = assertThrows<IllegalStateException> { f.create() }
        log("capacity overflow", listOf(ex.message ?: "no msg"))
    }
}
