package year2024.day08

import kotlin.math.max
import year2024.solveIt

typealias Pos = Pair<Int, Int>

fun main() {
    val day = "08"

    val expectedTest1 = 14
    val expectedTest2 = 34

    fun isInsideBound(it: Pos, bounds: Pair<IntRange, IntRange>) = it.first in bounds.first && it.second in bounds.second

    fun getOpposite(a: Pos, b: Pos, bounds: Pair<IntRange, IntRange>, limit: Int = 1): List<Pos> {
        val deltaY = a.first - b.first
        val deltaX = a.second - b.second

        val possible = 1..max(bounds.first.last, bounds.second.last)
        val oppositeA = possible.map { a.first + it * deltaY to a.second + it * deltaX }.takeWhile { isInsideBound(it, bounds) }.take(limit)
        val oppositeB = possible.map { b.first - it * deltaY to b.second - it * deltaX }.takeWhile { isInsideBound(it, bounds) }.take(limit)
        return listOf(oppositeA, oppositeB).flatten()
    }

    fun getCombinations(a: List<Pos>): List<Pair<Pos, Pos>> = a.flatMapIndexed { i, pa -> a.drop(i + 1).map { pa to it } }

    fun getAntennasLocations(input: List<String>): Map<Char, List<Pos>> =
        input.indices.flatMap { index -> input[0].indices.map { index to it } }.filterNot { input[it.first][it.second] == '.' }
            .groupBy { input[it.first][it.second] }

    fun part1(input: List<String>): Int {
        val antennasByFrequency = getAntennasLocations(input)
        val bounds = input.indices to input[0].indices

        val distinct = antennasByFrequency.values.asSequence().flatMap { pos -> getCombinations(pos) }
            .flatMap { getOpposite(it.first, it.second, bounds) }.distinct()
        return distinct.count()
    }


    fun part2(input: List<String>): Int {
        val antennasByFrequency = getAntennasLocations(input)
        val maxHarmonicas = max(input.size, input[0].length)
        val bounds = input.indices to input[0].indices

        val frequencyHarmonics: Sequence<Pos> = antennasByFrequency.values.asSequence().flatMap { pos -> getCombinations(pos) }
            .flatMap { getOpposite(it.first, it.second, bounds, maxHarmonicas) }
        val positionsWithFrequencies: Sequence<Pos> = frequencyHarmonics + antennasByFrequency.values.flatten()
        return positionsWithFrequencies.distinct().count()
    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}