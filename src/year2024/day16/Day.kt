package year2024.day16

import java.util.PriorityQueue
import year2024.solveIt

enum class Direction {
    WEST, NORTH, EAST, SOUTH
}

data class Position(val positions: List<Pair<Int,Int>>, val direction: Direction, val cost: Int ) {
    val current = positions.last()
        get() = field

    fun walk(): List<Position> = listOf(
        this.copy(
            positions = positions + (current.first to current.second - 1), direction = Direction.WEST, cost = this.cost + when (this.direction) {
                Direction.WEST -> 1
                Direction.EAST -> 2001
                Direction.NORTH, Direction.SOUTH -> 1001
            }
        ),
        this.copy(
            positions + (current.first to current.second + 1), direction = Direction.EAST, cost = this.cost + when (this.direction) {
                Direction.EAST -> 1
                Direction.WEST -> 2001
                Direction.NORTH, Direction.SOUTH -> 1001
            }
        ),
        this.copy(
            positions + (current.first - 1 to current.second), direction = Direction.NORTH, cost = this.cost + when (this.direction) {
                Direction.WEST -> 1001
                Direction.EAST -> 1001
                Direction.NORTH -> 1
                Direction.SOUTH -> 2001
            }
        ),
        this.copy(
            positions + (current.first + 1 to current.second), direction = Direction.SOUTH, cost = this.cost + when (this.direction) {
                Direction.WEST -> 1001
                Direction.EAST -> 1001
                Direction.NORTH -> 2001
                Direction.SOUTH -> 1
            }
        ),
    ).filter { steps ->
        this.positions.none { p -> steps.positions.last() == p }
    }
}

fun main() {
    val day = "16"

    val expectedTest1 = 11048
    val expectedTest2 = 64


    fun getPosition(input: List<String>, c: Char) = input.mapIndexed { l, line -> l to line.indexOf(c) }.first { it.second != -1 }

    fun getSolutions(input: List<String>): MutableSet<Position> {
        val start = getPosition(input, 'S')
        val end = getPosition(input, 'E')

        val queue = PriorityQueue<Position>(compareBy { it.cost })
        queue.add(Position(listOf(start), Direction.EAST, 0))

        val visitedPositions = mutableMapOf<Pair<Pair<Int, Int>, Direction>, Int>()

        val solutions = mutableSetOf<Position>()
        while (queue.isNotEmpty()) {
            val poll = queue.poll()
            if (poll.current == end) {
                solutions.add(poll)
            }
            if (poll.cost > (solutions.firstOrNull()?.cost ?: Int.MAX_VALUE)) {
                break
            }
            poll.walk().filter { input[it.current.first][it.current.second] != '#' }
                .filter { next ->
                    val currentCost = visitedPositions.getOrDefault(Pair(next.current, next.direction), Int.MAX_VALUE)
                    if (next.cost <= currentCost) {
                        visitedPositions[Pair(next.current, next.direction)] = next.cost // Update cost
                        true
                    } else {
                        false
                    }
                }
                .forEach { queue.add(it) }
        }
        return solutions
    }

    fun part1(input: List<String>): Int {
        val solutions1 = getSolutions(input)
        return solutions1.first().cost
    }

    fun part2(input: List<String>): Int {
       return getSolutions(input).flatMap { it.positions }.toSet().size
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




