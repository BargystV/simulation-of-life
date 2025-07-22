import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.system.measureNanoTime


private const val NUM_ENTITIES = 1_000_000
private const val REPEATS = 50

private class AIComponent {
    var state = 0
    fun update() {
        state = (state + 1) % 1000
    }
}

private class GameEntity(val ai: AIComponent)

class PerformanceTest {

    @Test
    fun testEntityComponentAccess() {
        val aiComponents = Array(NUM_ENTITIES) { AIComponent() }
        val entities = Array(NUM_ENTITIES) { GameEntity(aiComponents[it]) }

        val timeNs = measureNanoTime {
            repeat(REPEATS) {
                for (i in 0 until NUM_ENTITIES) {
                    entities[i].ai.update()
                }
            }
        }

        val seconds = timeNs / 1e9
        println("testEntityComponentAccess: $seconds сек")
    }

    @Test
    fun testFlatComponentAccess() {
        val aiComponents = Array(NUM_ENTITIES) { AIComponent() }

        val timeNs = measureNanoTime {
            repeat(REPEATS) {
                for (i in 0 until NUM_ENTITIES) {
                    aiComponents[i].update()
                }
            }
        }

        val seconds = timeNs / 1e9
        println("testFlatComponentAccess: $seconds сек")
    }

    @Test
    fun compareFlatVsEntity() {
        val flatAI = Array(NUM_ENTITIES) { AIComponent() }
        val entityAI = Array(NUM_ENTITIES) { AIComponent() }
        val entities = Array(NUM_ENTITIES) { GameEntity(entityAI[it]) }

        val flatTime = measureNanoTime {
            repeat(REPEATS) {
                for (i in 0 until NUM_ENTITIES) {
                    flatAI[i].update()
                }
            }
        }

        val entityTime = measureNanoTime {
            repeat(REPEATS) {
                for (i in 0 until NUM_ENTITIES) {
                    entities[i].ai.update()
                }
            }
        }

        val flatSec = flatTime / 1e9
        val entitySec = entityTime / 1e9

        println("FlatComponentTest:   $flatSec сек")
        println("EntityComponentTest: $entitySec сек")

        // Утверждаем, что flat должен быть быстрее
        assertTrue(entityTime > flatTime, "Flat access должен быть быстрее, чем Entity + pointer access")
    }
}
