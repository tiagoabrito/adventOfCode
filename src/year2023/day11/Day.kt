package year2023.day11

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import year2023.solveIt


fun main() {
    val day = "11"

    val expectedTest1 = 374L
    val expectedTest2 = 82000210L


    fun getDistance(
        expansionItems: Set<Int>, factor: Long, a: Int, b: Int
    ): Long {
        val expansionsX = (min(a, b)..max(a, b)).count { expansionItems.contains(it) }
        return abs(b - a) + factor * expansionsX - expansionsX
    }

    fun getDistance(g1: Pair<Int, Int>, g2: Pair<Int, Int>, expansionLines: Set<Int>, expansionColumns: Set<Int>, factor: Long): Long {
        val dx = getDistance(expansionColumns, factor, g1.second, g2.second)
        val dy = getDistance(expansionLines, factor, g1.first, g2.first)
        return dx + dy
    }

    fun getIt(input: List<String>, expansionFactor: Long): Long {
        val galaxies = input.flatMapIndexed { l, line -> line.mapIndexedNotNull { c, char -> if (char == '#') l to c else null } }
        val galaxiesConnections = galaxies.flatMapIndexed { index: Int, gal: Pair<Int, Int> -> galaxies.drop(index + 1).map { gal to it } }

        val expansionLines = input.indices.filter { idx -> galaxies.none { it.first == idx } }.toSet()
        val expansionColumns = input[0].indices.filter { idx -> galaxies.none { it.second == idx } }.toSet()


        return galaxiesConnections.stream().parallel().mapToLong { c-> getDistance(c.first, c.second, expansionLines, expansionColumns, expansionFactor) }.sum()
    }

    fun part1(input: List<String>): Long {
        return getIt(input, 2L)
    }

    fun part2(input: List<String>): Long {
        return getIt(input, 1000000L)
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




