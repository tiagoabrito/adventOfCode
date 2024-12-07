package year2024.day06

import year2024.solveIt


enum class Direction {
    RIGHT, DOWN, LEFT, UP
}

fun main() {
    val day = "06"

    val expectedTest1 = 41
    val expectedTest2 = 6

    fun walk(pos: Pair<Int, Int>, direction: Direction) =
        when (direction) {
            Direction.RIGHT -> pos.first to pos.second + 1
            Direction.LEFT -> pos.first to pos.second - 1
            Direction.UP -> pos.first - 1 to pos.second
            Direction.DOWN -> pos.first + 1 to pos.second
        }

    fun isInMap(currentPosition: Pair<Int, Int>, input: List<String>) =
        currentPosition.first in input.indices && currentPosition.second in input[0].indices

    fun getStartPosition(input: List<String>) = input.mapIndexed { i, l -> i to l.indexOf('^') }.first { it.second != -1 }

    fun part1(input: List<String>): Int {
        var currentPosition = getStartPosition(input)
        var currentDirection = Direction.UP

        val visited = mutableSetOf<Pair<Pair<Int, Int>, Direction>>()

        while (isInMap(currentPosition, input)) {
            visited.add(currentPosition to currentDirection)
            val nextPosition = walk(currentPosition, currentDirection)
            if (isInMap(nextPosition, input) && input[nextPosition.first][nextPosition.second] == '#') {
                currentDirection = Direction.values()[(currentDirection.ordinal + 1) % Direction.values().size]
            } else {
                currentPosition = nextPosition
            }
        }
        return visited.map { it.first }.distinct().size
    }

    fun hasLoop(input: List<String>, start: Pair<Int, Int>): Boolean {
        var currentPosition = start
        var currentDirection = Direction.UP

        val visited = mutableSetOf<Pair<Pair<Int, Int>, Direction>>()

        while (isInMap(currentPosition, input)) {
            if (!visited.add(currentPosition to currentDirection)) {
                return true
            }
            val nextPosition = walk(currentPosition, currentDirection)
            if (isInMap(nextPosition, input) && input[nextPosition.first][nextPosition.second] == '#') {
                currentDirection = Direction.values()[(currentDirection.ordinal + 1) % Direction.values().size]
            } else {
                currentPosition = nextPosition
            }
        }
        return false
    }

    fun applyBlockage(input: List<String>, p: Pair<Int, Int>) = input.mapIndexed { index, l ->
        when (index == p.first) {
            true -> l.replaceRange(p.second, p.second + 1, "#")
            false -> l
        }
    }

    fun part2(input: List<String>): Int {
        val start = getStartPosition(input)

        return input.indices.flatMap { l -> input[l].indices.map { l to it } }
            .map { p -> applyBlockage(input, p) }
            .count { hasLoop(it, start) }
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}