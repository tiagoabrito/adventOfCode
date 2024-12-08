package year2024.day08

import year2024.solveIt

typealias Pos = Pair<Int, Int>

fun main() {
    val day = "08"

    val expectedTest1 = 14
    val expectedTest2 = 0L

    fun getOpposite(a:Pair<Int, Int>, b:Pair<Int, Int>): List<Pair<Int, Int>> {
        val deltaY = a.first - b.first
        val deltaX = a.second - b.second

        val oppositeA = (a.first + deltaY) to (a.second + deltaX)
        val oppositeB = (b.first - deltaY) to (b.second - deltaX)
        return listOf(oppositeA, oppositeB)
    }

    fun getCombinations(a:List<Pos>): List<Pair<Pos, Pos>> = a.flatMapIndexed { i, pa -> a.drop(i+1).map { pa to it } }

    fun part1(input: List<String>): Int {

        val antenasByFrequency = input.indices.flatMap { index -> input[0].indices.map { index to it } }
            .filterNot { input[it.first][it.second] == '.' }
            .groupBy { input[it.first][it.second] }

        val distinct = antenasByFrequency.values.asSequence().flatMap { pos -> getCombinations(pos) }
            .flatMap { getOpposite(it.first, it.second) }
            .filter { it.first in input.indices && it.second in input[0].indices }
            .distinct()
        return  distinct.count()
    }


    fun part2(input: List<String>): Int {
        return 0


    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}