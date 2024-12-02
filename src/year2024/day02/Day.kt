package year2024.day02

import year2024.solveIt


fun main() {
    val day = "02"
    val expectedTest1 = 2
    val expectedTest2 = 4


    fun isValidDecrease(a: Int, b: Int) = b - a in 1..3
    fun isValidIncrease(a: Int, b: Int) = a - b in 1..3

    fun isValidLine(line: List<Int>): Boolean {
        val pairs = line.zipWithNext()
        return pairs.all { isValidIncrease(it.first, it.second) } || pairs.all { isValidDecrease(it.first, it.second) }
    }

    fun getInts(it: String) = it.split(" ").map { it.toInt() }


    fun lineCombinations(it: String): List<List<Int>> {
        val values = getInts(it)
        return List(values.size) { i -> values.filterIndexed { index, _ -> index != i } }
    }

    fun part1(input: List<String>) = input.map { getInts(it) }.count { line -> isValidLine(line) }

    fun part2(input: List<String>): Int = input.map { lineCombinations(it) }.count { combinations -> combinations.any { isValidLine(it) } }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}