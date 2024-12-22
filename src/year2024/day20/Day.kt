package year2024.day20

import kotlin.math.abs
import year2024.solveIt


fun main() {
    val day = "20"

    val expectedTest1 = 0
    val expectedTest2 = 0

    fun getPosition(input: List<String>, c: Char) = input.mapIndexed { l, line -> l to line.indexOf(c) }.first { it.second != -1 }

    fun getNext(current: Pair<Int, Int>, previous: Pair<Int, Int>?, input: List<String>) =
        listOf(
            current.first to current.second - 1,
            current.first to current.second + 1,
            current.first - 1 to current.second,
            current.first + 1 to current.second,
        ).filter { input[it.first][it.second] != '#' }.first { it != previous }


    fun getPath(input: List<String>): MutableList<Pair<Int, Int>> {
        val start = getPosition(input, 'S')
        val end = getPosition(input, 'E')

        val path = mutableListOf(start)
        while (path.last() != end) {
            val next = getNext(path.last(), path.dropLast(1).lastOrNull(), input)
            path.add(next)
        }
        return path
    }

    fun getShortcutLength(a: Pair<Int, Int>, b: Pair<Int, Int>): Int {
        val dy = a.first - b.first
        val dx = a.second - b.second
        return abs(dy) + abs(dx)
    }

    fun getShortcuts(path: MutableList<Pair<Int, Int>>, maxCheat: Int): List<Pair<Pair<Int, Int>, Int>> = path.flatMapIndexed { i, p ->
        path.indices.filter { it > i + 2 }.map { (i to it)  to getShortcutLength(p, path[it]) }.filter { it.second <= maxCheat }
    }

    fun part1(input: List<String>): Int {
        val path = getPath(input)
        val shortCuts = getShortcuts(path, 2)

        return shortCuts.count { (a, d) -> ((a.second - a.first) - d) >= 100 }
    }


    fun part2(input: List<String>): Int {
        val path = getPath(input)
        val shortCuts = getShortcuts(path, 20)

        return shortCuts.count { (a, d) -> ((a.second - a.first) - d) >= 100 }
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}