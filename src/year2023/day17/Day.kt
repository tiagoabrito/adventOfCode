package year2023.day17

import java.util.PriorityQueue
import year2023.solveIt


fun main() {
    val day = "17"

    val expectedTest1 = 102L
    val expectedTest2 = 94L

    data class Position(val x: Int, val y: Int)

    data class Path(val heat: Long, val current: Position, val moves: List<Position>, val max: Int, val min: Int) {
        val maxSameDirection = max + 1
        val minSameDirection = min
        fun isValid(): Boolean = isNotContinuesSingleDirection() && isNewPosition() && isEnoughSingleDirection()

        private fun isNewPosition() = moves.count { it == current } == 1

        private fun isEnoughSingleDirection(): Boolean {
            if(minSameDirection == 1 || moves.size ==2){
                return true
            }
            val prev = moves.takeLast(2)[0]
            if (prev.x == current.x){
                val previousX = moves.reversed().dropWhile { it.x == current.x }
                return previousX.isEmpty() || (
                        previousX.take(minSameDirection).takeWhile { it.y == previousX[0].y }.size == minSameDirection)
            }
            else {
                val previousY = moves.reversed().dropWhile { it.y == current.y }
                return previousY.isEmpty() || (
                        previousY.take(minSameDirection).takeWhile { it.x == previousY[0].x }.size == minSameDirection)
            }
        }

        private fun isNotContinuesSingleDirection(): Boolean {
            val lastFour = moves.takeLast(maxSameDirection)
            return !(lastFour.all { it.x == current.x } || lastFour.all { it.y == current.y }) || lastFour.size != maxSameDirection
        }

        fun getLastDir(): String {
            return moves.takeLast(maxSameDirection).joinToString(",") { "(${it.x} ${it.y})" }
        }

    }


    fun getShortestPath(input: List<String>, min: Int, max: Int): Path {
        val priorityQueue = PriorityQueue<Path>(Comparator.comparing { it.heat })
        var actual = Path(0, Position(0, 0), listOf(Position(0, 0)), max, min)
        val destination = Position(input[0].length - 1, input.size - 1)
        val validX = 0 until input[0].length
        val validY = input.indices
        priorityQueue.add(actual)
        val visited = HashSet<String>()

        while (true) {
            actual = priorityQueue.remove()
            if (actual.current == destination && (
                        actual.moves.takeLast(min).map { it.x }.distinct().count() == 1 ||
                        actual.moves.takeLast(min).map { it.y }.distinct().count() == 1)) {
                break
            }
            val pos = actual.current
            val newPos = listOf(
                Position(pos.x - 1, pos.y),
                Position(pos.x + 1, pos.y),
                Position(pos.x, pos.y - 1),
                Position(pos.x, pos.y + 1)
            ).asSequence()
                .filter { it.x in validX && it.y in validY }
                .filter { actual.moves.elementAtOrNull(actual.moves.size - 1) != it }
                .filter { actual.moves.none { m -> m == it } }
                .map { actual.copy(heat = actual.heat + input[it.y][it.x].toString().toLong(), current = it, moves = (actual.moves + it)) }
                .filter { it.isValid() }
                .filter { visited.add("${it.current.x} ${it.current.y}, ${it.getLastDir()}") }
                .toList()

            newPos.forEach { priorityQueue.add(it) }
        }
        return actual
    }

    fun part1(input: List<String>): Long {

         val actual = getShortestPath(input,1,4)

         return actual.heat
    }

    fun part2(input: List<String>): Long {
        val actual = getShortestPath(input, 4, 10)

        return actual.heat
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




