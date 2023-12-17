package year2023.day16

import java.util.LinkedList
import java.util.Queue
import year2023.solveIt

enum class Direction {
    RIGHT, LEFT, UP, DOWN
}

data class Position(val line: Int, val column: Int) {
    fun nav(other: Position): Position = Position(line + other.line, column + other.column)

    fun nav(direction: Direction): Position = when (direction) {
        Direction.RIGHT -> Position(line, column + 1)
        Direction.LEFT -> Position(line, column - 1)
        Direction.UP -> Position(line - 1, column)
        Direction.DOWN -> Position(line + 1, column)
    }

    fun isBounded(maxLine: Int, maxColumn: Int) = line in 0 until maxLine && column in 0 until maxColumn
}

fun main() {
    val day = "16"

    val expectedTest1 = 46L
    val expectedTest2 = 51L

    val navigateRIGHT: Map<Char, Sequence<Direction>> = mapOf(
        '\\' to sequenceOf(Direction.DOWN),
        '/' to sequenceOf(Direction.UP),
        '|' to sequenceOf(Direction.UP, Direction.DOWN),
        '-' to sequenceOf(Direction.RIGHT),
        '.' to sequenceOf(Direction.RIGHT),
    )

    val navigateLEFT: Map<Char, Sequence<Direction>> = mapOf(
        '\\' to sequenceOf(Direction.UP),
        '/' to sequenceOf(Direction.DOWN),
        '|' to sequenceOf(Direction.UP, Direction.DOWN),
        '-' to sequenceOf(Direction.LEFT),
        '.' to sequenceOf(Direction.LEFT),
    )


    val navigateUP: Map<Char, Sequence<Direction>> = mapOf(
        '\\' to sequenceOf(Direction.LEFT),
        '/' to sequenceOf(Direction.RIGHT),
        '-' to sequenceOf(Direction.LEFT, Direction.RIGHT),
        '|' to sequenceOf(Direction.UP),
        '.' to sequenceOf(Direction.UP),
    )

    val navigateDOWN: Map<Char, Sequence<Direction>> = mapOf(
        '\\' to sequenceOf(Direction.RIGHT),
        '/' to sequenceOf(Direction.LEFT),
        '-' to sequenceOf(Direction.LEFT, Direction.RIGHT),
        '|' to sequenceOf(Direction.DOWN),
        '.' to sequenceOf(Direction.DOWN),
    )

    val navMap = mapOf(
        Direction.RIGHT to navigateRIGHT, Direction.LEFT to navigateLEFT, Direction.UP to navigateUP, Direction.DOWN to navigateDOWN
    )


    fun navigate(map: List<String>, p: Position, direction: Direction): Sequence<Pair<Position, Direction>> {
        val current = map[p.line][p.column]
        val sequence = navMap[direction]!![current]!!
        return sequence.map { p.nav(it) to it }.filter { it.first.isBounded(map.size, map[0].length) }
    }


    fun getLightened(input: List<String>, start: Position, startDirection: Direction): Long {
        val navigatedMap = mapOf(
            Direction.RIGHT to mutableSetOf<Position>(),
            Direction.LEFT to mutableSetOf(),
            Direction.UP to mutableSetOf(),
            Direction.DOWN to mutableSetOf()
        )

        val toNavigate: Queue<Pair<Position, Direction>> = LinkedList()
        toNavigate.add(start to startDirection)
        while (toNavigate.isNotEmpty()) {
            val (position, direction) = toNavigate.remove()
            if (navigatedMap[direction]?.add(position) == true) {
                navigate(input, position, direction).forEach { toNavigate.add(it) }
            }
        }

        return navigatedMap.values.flatten().distinct().count().toLong()
    }

    fun part1(input: List<String>): Long {
        return getLightened(input, Position(0, 0), Direction.RIGHT)
    }

    fun part2(input: List<String>): Long {
       return listOf(input.indices.map { i -> Position(i, 0) to Direction.RIGHT },
            input.indices.map { i -> Position(i, input[0].length - 1) to Direction.LEFT },
            input[0].indices.map { i -> Position(0, i) to Direction.DOWN },
            input[0].indices.map { i -> Position(input.size - 1, i) to Direction.UP }).flatten()
            .maxOfOrNull { getLightened(input, it.first, it.second) }?:0;
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




