package year2024.day07

import year2024.solveIt


fun main() {
    val day = "07"
    val expectedTest1 = 3749L
    val expectedTest2 = 11387L

    val applyOperations1: (Long, Long) -> List<Long> = { a, b -> listOf(a + b, a * b) }
    val applyOperations2: (Long, Long) -> List<Long> = { a, b -> listOf(a + b, a * b, "$a$b".toLong()) }

    fun isPossible(expected: Long, actual: Long, elements: List<Long>, applyOperations: (Long, Long) -> List<Long>): Boolean {
        if (actual > expected || elements.isEmpty()) {
            return expected == actual
        }
        return applyOperations(actual, elements.first()).any { isPossible(expected, it, elements.drop(1), applyOperations) }
    }

    fun getSumOfValidOperations(input: List<String>, function: (Long, Long) -> List<Long>) =
        input.map { it.split(":") }.map { it[0].toLong() to it[1].split(" ").filter { it.isNotBlank() }.map { v -> v.toLong() } }
            .filter { (expected, values) -> isPossible(expected, values.first(), values.drop(1), function) }
            .sumOf { it.first }

    fun part1(input: List<String>): Long {
        return getSumOfValidOperations(input, applyOperations1)
    }

    fun part2(input: List<String>): Long {
        return getSumOfValidOperations(input, applyOperations2)
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}