package year2024.day09

import year2024.solveIt


fun main() {
    val day = "09"

    val expectedTest1 = 114L
    val expectedTest2 = 2L

    fun solveIt(values: List<Long>): Long {
        if (values.all { it == 0L }) {
            return 0L;
        }
        val rest = solveIt(values.zipWithNext { a, b -> b - a })
        return values.last() + rest
    }


    fun part1(input: List<String>): Long {
        return input.map { it.split(" ").map { n -> n.toLong() } }.sumOf { solveIt(it) }
    }

    fun part2(input: List<String>): Long {
        return input.map { it.split(" ").map { n -> n.toLong() } }.sumOf { solveIt(it.reversed()) }
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




