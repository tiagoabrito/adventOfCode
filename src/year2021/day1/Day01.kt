package year2021.day1

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.toInt() }.zipWithNext().filter { it.first < it.second }.size
    }

    fun part2(input: List<String>): Int {
        return input.map { it.toInt() }.windowed(3).map { it.sum() }.zipWithNext().filter { it.first < it.second }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2021/Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("year2021/Day01")
    println(part1(input))
    println(part2(input))
}
