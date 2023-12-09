package year2023.day08

import year2023.getLCM
import year2023.solveIt


fun main() {
    val day = "08"

    val expectedTest1 = 6L
    val expectedTest2 = 6L

    fun navigateToNext(
        instructions: String, currIdx: Int, paths: Map<String, Pair<String, String>>, current: String
    ) = if (instructions[currIdx] == 'L') paths[current]!!.first else paths[current]!!.second

    fun solveIt(
        current: String, instructions: String, paths: Map<String, Pair<String, String>>
    ): Long {
        var actual = current
        var i = 0L
        while (actual[2] != 'Z') {
            val currIdx = (i % instructions.length).toInt()
            actual = navigateToNext(instructions, currIdx, paths, actual)
            i++
        }
        return i
    }

    fun part1(input: List<String>): Long {
        val instructions = input[0]
        val paths = input.drop(2).associate { it.substring(0, 3) to (it.substring(7, 10) to it.substring(12, 15)) }

        return solveIt("AAA", instructions, paths)
    }

    fun part2(input: List<String>): Long {
        val instructions = input[0]

        val paths = input.drop(2).map { it.substring(0, 3) to (it.substring(7, 10) to it.substring(12, 15)) }.toMap()

        val solutions = paths.filter { it.key.endsWith('A') }.map { it.key }.map { solveIt(it, instructions, paths) }

        return solutions.chunked(2).map { getLCM(it[0], it[1]) }.fold(1L) { acc, l -> getLCM(acc, l) }
    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test2")
}



